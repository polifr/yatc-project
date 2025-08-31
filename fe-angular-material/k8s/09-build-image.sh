#!/bin/sh

rm -rf $PWD/../dist

docker run -it --rm -v $PWD/..:/opt/node-src -w /opt/node-src node:24-alpine sh -c "npm install -g @angular/cli && npm install && ng build --configuration production --base-href /angular-material/"

eval $(minikube -p minikube docker-env)

export MODULE_NAME="yatc/fe-angular-material"
docker build --tag ${MODULE_NAME} --label ${MODULE_NAME} -f ../Dockerfile.k8s ../. 
docker image prune --force --filter='label='"${MODULE_NAME}"''
