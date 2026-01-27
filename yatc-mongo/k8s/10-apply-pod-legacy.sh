#!/bin/sh

kubectl apply -f yatc-mongo-deployment-legacy.yaml
kubectl apply -f yatc-mongo-service.yaml

kubectl apply -f yatc-mongo-exporter-deployment.yaml
kubectl apply -f yatc-mongo-exporter-service.yaml
