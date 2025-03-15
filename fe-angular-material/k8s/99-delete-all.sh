#!/bin/sh

kubectl delete service fe-angular-material-exporter
kubectl delete deployment fe-angular-material-exporter
kubectl delete service fe-angular-material
kubectl delete deployment fe-angular-material
