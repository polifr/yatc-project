#!/bin/sh

kubectl delete service yatc-grafana
kubectl delete deployment yatc-grafana

kubectl delete configmap yatc-grafana-dashboard-9628
kubectl delete configmap yatc-grafana-dashboard-11506
kubectl delete configmap yatc-grafana-dashboard-14390
kubectl delete configmap yatc-grafana-dashboard-14900
kubectl delete configmap yatc-grafana-dashboard-14997
kubectl delete configmap yatc-grafana-dashboard-18276
kubectl delete configmap yatc-grafana-dashboard-19004
kubectl delete configmap yatc-grafana-dashboard-23019

kubectl delete configmap yatc-grafana-config
