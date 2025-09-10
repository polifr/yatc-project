#!/bin/sh

./90-delete-ingress.sh
./99-delete-cluster.sh
./01-create-cluster.sh
./02-enable-addons.sh
./10-apply-ingress.sh
./20-apply-clusterrole.sh
