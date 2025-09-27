#!/bin/sh

docker run -it --rm -v $PWD/..:/opt/maven -v $HOME/.m2:/opt/maven-repo/.m2 -w /opt/maven maven:3-eclipse-temurin-25 mvn -Duser.home=/opt/maven-repo -Dmaven.test.skip=true clean package

eval $(minikube -p minikube docker-env)

export MODULE_NAME="yatc/ms-spring-servlet"
docker build --tag ${MODULE_NAME} --label ${MODULE_NAME} -f ../Dockerfile.k8s ../. 
docker image prune --force --filter='label='"${MODULE_NAME}"''
