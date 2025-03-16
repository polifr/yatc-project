#!/bin/sh

kubectl apply -f fe-react-mui-deployment.yaml
kubectl apply -f fe-react-mui-service.yaml
kubectl apply -f fe-react-mui-exporter-deployment.yaml
kubectl apply -f fe-react-mui-exporter-service.yaml
