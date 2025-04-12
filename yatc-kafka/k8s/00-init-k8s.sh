#!/bin/sh

./99-delete-all.sh
sleep 5
./01-create-configmap.sh
sleep 5
./10-apply-pod.sh
