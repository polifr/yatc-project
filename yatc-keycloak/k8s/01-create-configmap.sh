#!/bin/sh

kubectl create configmap yatc-keycloak-config --from-file ../realms/yatc-realm.json
