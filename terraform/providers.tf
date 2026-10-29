terraform {
  required_version = ">= 1.5.0"

  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

# Le jeton d'API n'est JAMAIS écrit dans le code.
# Il est lu dans la variable d'environnement DIGITALOCEAN_TOKEN.
provider "digitalocean" {}