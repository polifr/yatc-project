#!/bin/sh

kubectl delete service yatc-loki
kubectl delete deployment yatc-loki
kubectl delete configmap yatc-loki-config
