#!/bin/sh

kubectl apply -f yatc-postgres-deployment.yaml
kubectl apply -f yatc-postgres-service.yaml

kubectl apply -f yatc-postgres-exporter-deployment.yaml
kubectl apply -f yatc-postgres-exporter-service.yaml
