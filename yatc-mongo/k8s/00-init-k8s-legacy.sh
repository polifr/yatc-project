#!/bin/sh

./99-delete-all.sh
./01-create-configmap.sh
./02-create-secret.sh
./10-apply-pod-legacy.sh
