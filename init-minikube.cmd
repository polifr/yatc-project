@echo off

cd k8s
call delete-ingress.cmd
call apply-ingress.cmd
cd ..

cd gw-spring-cloud\k8s
call 00-init-k8s
cd ..\..

cd yatc-grafana\k8s
call 99-delete-all.cmd
call 01-create-configmap.cmd
call 10-apply-pod.cmd
cd ..\..

cd yatc-keycloak\k8s
call 00-init-k8s
cd ..\..

cd yatc-postgres\k8s
call 99-delete-all.cmd
call 01-create-configmap.cmd
call 02-create-secret.cmd
call 10-apply-pod.cmd
cd ..\..

cd yatc-prometheus\k8s
call 99-delete-all.cmd
call 01-create-configmap.cmd
call 10-apply-pod.cmd
cd ..\..

cd yatc-redis\k8s
call 00-init-k8s
cd ..\..
