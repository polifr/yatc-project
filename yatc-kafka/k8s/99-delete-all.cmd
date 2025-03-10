@echo off

kubectl delete service yatc-kafka-exporter
kubectl delete deployment yatc-kafka-exporter
kubectl delete service yatc-kafka
kubectl delete deployment yatc-kafka
kubectl delete configmap yatc-kafka-exporter-config
