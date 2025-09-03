@echo off

cd k8s
call 01-create-cluster
call 02-enable-addons
call 10-apply-ingress
call 20-apply-clusterrole
cd ..

@REM Inizializzazione infrastruttura

cd yatc-redis\k8s
call 00-init-k8s
cd ..\..

cd yatc-postgres\k8s
call 00-init-k8s
cd ..\..

cd yatc-mongo\k8s
call 00-init-k8s
cd ..\..

cd yatc-kafka
call 00-init-k8s
cd ..\..

cd yatc-keycloak\k8s
call 00-init-k8s
cd ..\..

cd yatc-alloy\k8s
call 00-init-k8s
cd ..\..

cd yatc-loki\k8s
call 00-init-k8s
cd ..\..

cd yatc-prometheus\k8s
call 00-init-k8s
cd ..\..

cd yatc-grafana\k8s
call 00-init-k8s
cd ..\..

@REM Inizializzazione microservizi

cd gw-spring-cloud\k8s
call 00-init-k8s
cd ..\..
