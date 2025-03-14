#!/bin/sh

# docker run -it --rm -u `id -u`:`id -g` -v "$PWD/..":/usr/src/mymaven -v ~/.m2:/var/maven/.m2 -w /usr/src/mymaven maven:3-eclipse-temurin-21 mvn -Duser.home=/var/maven -Dmaven.test.skip=true clean package

docker run -it --rm -v "$(pwd)/..":/opt/maven -v "$(home)/.m2":/opt/maven-repo/.m2 -w /opt/maven maven:3-eclipse-temurin-21 mvn -Duser.home=/opt/maven-repo -Dmaven.test.skip=true clean package

eval $(minikube -p minikube docker-env)

export MODULE_NAME="gw-spring-cloud"
docker build --tag ${MODULE_NAME} --label ${MODULE_NAME} -f ../Dockerfile.k8s ../. 
docker image prune --force --filter='label='"${MODULE_NAME}"''
