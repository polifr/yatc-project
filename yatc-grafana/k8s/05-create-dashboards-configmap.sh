#!/bin/sh

# Postgres exporter
kubectl create configmap yatc-grafana-dashboard-9628 --from-file ../dashboards/9628_rev8.json

# Spring Cloud Gateway
kubectl create configmap yatc-grafana-dashboard-11506 --from-file ../dashboards/11506_rev1.json

# KeyCloak
kubectl create configmap yatc-grafana-dashboard-14390 --from-file ../dashboards/14390_rev7.json

# Nginx
kubectl create configmap yatc-grafana-dashboard-14900 --from-file ../dashboards/14900_rev2.json

# MongoDB
kubectl create configmap yatc-grafana-dashboard-14997 --from-file ../dashboards/14997_rev3.json

# Kafka
kubectl create configmap yatc-grafana-dashboard-18276 --from-file ../dashboards/18276_rev1.json

# Spring Boot
kubectl create configmap yatc-grafana-dashboard-19004 --from-file ../dashboards/19004_rev1.json

# NodeJS
kubectl create configmap yatc-grafana-dashboard-23019 --from-file ../dashboards/23019_rev1.json
