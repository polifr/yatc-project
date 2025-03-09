@echo off

kubectl apply -f yatc-kafka-deployment.yaml
kubectl apply -f yatc-kafka-service.yaml
kubectl apply -f yatc-kafka-exporter-deployment.yaml
kubectl apply -f yatc-kafka-exporter-service.yaml
