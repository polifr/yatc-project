#!/bin/sh

# Postgres exporter
kubectl create configmap yatc-grafana-dashboard-9628 --from-file ../dashboards/9628_rev8.json

# Spring Cloud Gateway
kubectl create configmap yatc-grafana-dashboard-11506 --from-file ../dashboards/11506_rev1.json

# Spring Boot
kubectl create configmap yatc-grafana-dashboard-19004 --from-file ../dashboards/19004_rev1.json

# TODO Altre dashboard da aggiungere:
# nginx per fe-* https://grafana.com/grafana/dashboards/14900-nginx/
# keycloak https://grafana.com/grafana/dashboards/14390-keycloak-metrics/
# mongo https://grafana.com/grafana/dashboards/14997-mongodb/
# kafka https://grafana.com/grafana/dashboards/18276-kafka-dashboard/
# nodejs https://grafana.com/grafana/dashboards/11956-nodejs-metrics/ https://grafana.com/grafana/dashboards/19062-nodejs-applications-dashboard/ https://grafana.com/grafana/dashboards/23019-ryzen/
