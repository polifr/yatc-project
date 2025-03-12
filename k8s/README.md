# Informazioni per configurazione Kubernetes locale - Minikube

## Attivazione Minikube e strumenti

TODO

## Inizializzazione cluster

Avviare il cluster con `minikube start`, dopodiché attivare le estensioni (visibili tramite `minikube addons list`), tramite:
- `minikube addons enable ingress`
- `minikube addons enable metrics-server`
- `minikube addons enable dashboard`
- Eseguire `minikube dashboard` per lanciare la dashboard

TODO: creare lo script di inizializzazione del cluster in modo che siano previste più risorse rispetto alle 2 cpu ed ai 2 GB di RAM standard di minikube (`init-cluster`) ed includere anche la creazione del clusterrole

## Attivazione forward Ingress
- Eseguire `minikube tunnel`, oppure in alternativa lo script `enable-tunnel`
