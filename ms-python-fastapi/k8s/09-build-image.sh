#!/bin/sh

eval $(minikube -p minikube docker-env)

export MODULE_NAME="yatc/ms-python-fastapi"
docker build --tag ${MODULE_NAME} --label ${MODULE_NAME} -f ../Dockerfile.k8s ../. 
docker image prune --force --filter='label='"${MODULE_NAME}"''
