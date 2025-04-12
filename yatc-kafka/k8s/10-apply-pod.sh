#!/bin/sh

kubectl apply -f yatc-kafka-service.yaml
sleep 3
kubectl apply -f yatc-kafka-deployment.yaml

kubectl apply -f yatc-kafka-exporter-service.yaml
sleep 3
kubectl apply -f yatc-kafka-exporter-deployment.yaml
