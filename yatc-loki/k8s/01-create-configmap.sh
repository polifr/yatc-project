#!/bin/sh

kubectl create configmap yatc-loki-config --from-file ../config/local-config.yaml
