# Récupère la dernière version Kubernetes disponible,
# sauf si une version est imposée avec la variable kubernetes_version.
data "digitalocean_kubernetes_versions" "latest" {
  count = var.kubernetes_version == null ? 1 : 0
}

locals {
  k8s_version = var.kubernetes_version != null ? var.kubernetes_version : data.digitalocean_kubernetes_versions.latest[0].latest_version
}

# --- Réseau privé ---
resource "digitalocean_vpc" "main" {
  name     = "${var.cluster_name}-vpc"
  region   = var.region
  ip_range = "10.10.0.0/16"
}

# --- Cluster Kubernetes managé (DOKS) ---
resource "digitalocean_kubernetes_cluster" "main" {
  name     = var.cluster_name
  region   = var.region
  version  = local.k8s_version
  vpc_uuid = digitalocean_vpc.main.id

  node_pool {
    name       = "default-pool"
    size       = var.node_size
    node_count = var.node_count
    tags       = ["f1hub"]
  }
}

# --- Pare-feu appliqué aux noeuds (via le tag "f1hub") ---
resource "digitalocean_firewall" "nodes" {
  name = "${var.cluster_name}-nodes"
  tags = ["f1hub"]

  inbound_rule {
    protocol         = "tcp"
    port_range       = "80"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  inbound_rule {
    protocol         = "tcp"
    port_range       = "443"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  # Plage NodePort utilisée par le load balancer pour joindre les services
  inbound_rule {
    protocol         = "tcp"
    port_range       = "30000-32767"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol              = "tcp"
    port_range            = "1-65535"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol              = "udp"
    port_range            = "1-65535"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol              = "icmp"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }
}

# --- Projet DigitalOcean : regroupe les ressources pour les retrouver facilement ---
resource "digitalocean_project" "f1hub" {
  name        = "f1-community-hub"
  description = "Projet portfolio DevOps : F1 Community Hub"
  purpose     = "Web Application"
  environment = "Development"
  resources   = [digitalocean_kubernetes_cluster.main.urn]
}