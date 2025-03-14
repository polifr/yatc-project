#!/bin/sh

kubectl delete service yatc-grafana
kubectl delete deployment yatc-grafana
kubectl delete configmap yatc-grafana-config
