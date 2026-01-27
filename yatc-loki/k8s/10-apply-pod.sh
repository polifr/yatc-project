#!/bin/sh

kubectl apply -f yatc-loki-deployment.yaml
kubectl apply -f yatc-loki-service.yaml
