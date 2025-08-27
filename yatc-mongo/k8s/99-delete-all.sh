#!/bin/sh

kubectl delete service yatc-mongo-exporter
kubectl delete deployment yatc-mongo-exporter
kubectl delete service yatc-mongo
kubectl delete deployment yatc-mongo

kubectl delete secret yatc-mongo-secret
kubectl delete secret yatc-mongo-init-secret

kubectl delete configmap yatc-mongo-init-config
