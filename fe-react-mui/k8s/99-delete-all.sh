#!/bin/sh

kubectl delete service fe-react-mui-exporter
kubectl delete deployment fe-react-mui-exporter
kubectl delete service fe-react-mui
kubectl delete deployment fe-react-mui
