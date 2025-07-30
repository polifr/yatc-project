#!/bin/sh

kubectl delete service yatc-alloy
kubectl delete daemonset yatc-alloy
kubectl delete clusterrolebinding yatc-alloy-discoverer
kubectl delete serviceaccount yatc-alloy
kubectl delete configmap yatc-alloy-config
