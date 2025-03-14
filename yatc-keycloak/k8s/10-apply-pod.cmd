@echo off

kubectl apply -f yatc-keycloak-deployment.yaml
kubectl apply -f yatc-keycloak-service.yaml
