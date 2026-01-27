#!/bin/sh

kubectl apply -f fe-angular-material-deployment.yaml
kubectl apply -f fe-angular-material-service.yaml
kubectl apply -f fe-angular-material-exporter-deployment.yaml
kubectl apply -f fe-angular-material-exporter-service.yaml
