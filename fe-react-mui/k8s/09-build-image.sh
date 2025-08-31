#!/bin/sh

rm -rf $PWD/../build

docker run -it --rm -v $PWD/..:/opt/node-src -w /opt/node-src node:24-alpine sh -c "npm install && npm run build"

eval $(minikube -p minikube docker-env)

export MODULE_NAME="yatc/fe-react-mui"
docker build --tag ${MODULE_NAME} --label ${MODULE_NAME} -f ../Dockerfile.k8s ../. 
docker image prune --force --filter='label='"${MODULE_NAME}"''
