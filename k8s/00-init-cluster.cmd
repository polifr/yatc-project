@echo off

call 90-delete-ingress
call 99-delete-cluster
call 01-create-cluster
call 02-enable-addons
call 10-apply-ingress
call 20-apply-clusterrole
