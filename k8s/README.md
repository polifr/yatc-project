# Informazioni per configurazione Kubernetes locale - Minikube

## Attivazione Minikube e strumenti

TODO

## Inizializzazione cluster

Avviare il cluster con `minikube start`, dopodiché attivare le estensioni (visibili tramite `minikube addons list`), tramite:
- `minikube addons enable ingress`
- `minikube addons enable metrics-server`
- `minikube addons enable dashboard`
  - Eseguire `minikube dashboard` per lanciare la dashboard

## Attivazione forward Ingress

Eseguire `minikube tunnel`
