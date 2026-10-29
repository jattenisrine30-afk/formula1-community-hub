output "cluster_id" {
  description = "Identifiant du cluster Kubernetes"
  value       = digitalocean_kubernetes_cluster.main.id
}

output "cluster_endpoint" {
  description = "Adresse de l'API Kubernetes"
  value       = digitalocean_kubernetes_cluster.main.endpoint
}

output "kubeconfig" {
  description = "Fichier kubeconfig pour se connecter au cluster"
  value       = digitalocean_kubernetes_cluster.main.kube_config[0].raw_config
  sensitive   = true
}