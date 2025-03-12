@echo off

cd k8s
call 02-enable-addons
call 05-enable-addons
call 10-apply-ingress
cd ..

cd gw-spring-cloud\k8s
call 00-init-k8s
cd ..\..

cd yatc-grafana\k8s
call 00-init-k8s
cd ..\..

cd yatc-keycloak\k8s
call 00-init-k8s
cd ..\..

cd yatc-postgres\k8s
call 00-init-k8s
cd ..\..

cd yatc-prometheus\k8s
call 00-init-k8s
cd ..\..

cd yatc-redis\k8s
call 00-init-k8s
cd ..\..

cd yatc-kafka
call 00-init-k8s
cd ..\..
