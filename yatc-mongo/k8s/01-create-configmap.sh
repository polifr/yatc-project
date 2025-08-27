#!/bin/sh

kubectl create configmap yatc-mongo-init-config --from-file ../init/mongo-init.js
