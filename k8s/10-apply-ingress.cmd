@echo off

minikube addons enable ingress

kubectl apply -f yatc-ingress.yaml
