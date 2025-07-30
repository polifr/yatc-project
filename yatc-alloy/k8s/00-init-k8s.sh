#!/bin/sh

./99-delete-all.sh
./01-create-configmap.sh
./10-apply-daemonset.sh
