#!/bin/sh

./99-delete-cluster.sh
./01-create-cluster.sh
./02-enable-addons.sh
./05-apply-clusterrole.sh
./10-apply-ingress.sh
