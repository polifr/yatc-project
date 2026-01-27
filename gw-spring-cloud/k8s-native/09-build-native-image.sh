#!/bin/sh

eval $(minikube -p minikube docker-env)

export MODULE_NAME="yatc/gw-spring-cloud"
docker build --tag ${MODULE_NAME} --label ${MODULE_NAME} -f ../Dockerfile.native ../. 
docker image prune --force --filter='label='"${MODULE_NAME}"''
