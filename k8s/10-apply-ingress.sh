#!/bin/sh

minikube addons enable ingress

kubectl apply -f yatc-ingress.yaml
