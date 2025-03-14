#!/bin/sh

./99-delete-all.sh
./01-create-configmap.sh
./03-apply-rbac.sh
./10-apply-pod.sh
