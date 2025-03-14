#!/bin/sh

docker run -it --rm -v $PWD/..:/opt/rust-src -w /opt/rust-src rust:bookworm /bin/bash -c "cargo build --release && strip target/release/ms-rust-axum"

eval $(minikube -p minikube docker-env)

export MODULE_NAME="yatc/ms-rust-axum"
docker build --tag ${MODULE_NAME} --label ${MODULE_NAME} -f ../Dockerfile.k8s ../. 
docker image prune --force --filter='label='"${MODULE_NAME}"''
