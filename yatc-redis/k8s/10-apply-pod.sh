#!/bin/sh

kubectl apply -f yatc-redis-deployment.yaml
kubectl apply -f yatc-redis-service.yaml
kubectl apply -f yatc-redis-exporter-deployment.yaml
kubectl apply -f yatc-redis-exporter-service.yaml
