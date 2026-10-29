variable "region" {
  description = "Région DigitalOcean (fra1 = Francfort, la plus proche du Maroc)"
  type        = string
  default     = "fra1"
}

variable "cluster_name" {
  description = "Nom du cluster Kubernetes"
  type        = string
  default     = "f1hub-cluster"
}

variable "node_size" {
  description = "Taille des noeuds (slug Droplet). Liste : doctl kubernetes options sizes"
  type        = string
  default     = "s-2vcpu-4gb"
}

variable "node_count" {
  description = "Nombre de noeuds dans le pool"
  type        = number
  default     = 2
}

variable "kubernetes_version" {
  description = "Slug de version Kubernetes (ex. 1.34.1-do.0). Si null, la dernière version disponible est récupérée via l'API."
  type        = string
  default     = null
}