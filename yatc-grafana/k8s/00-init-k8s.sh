#!/bin/sh

./99-delete-all.sh
./01-create-configmap.sh
./05-create-dashboards-configmap.sh
./10-apply-pod.sh
