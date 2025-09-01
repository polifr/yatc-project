#!/bin/sh

kubectl apply -f yatc-dependencytrack-deployment.yaml
kubectl apply -f yatc-dependencytrack-service.yaml
