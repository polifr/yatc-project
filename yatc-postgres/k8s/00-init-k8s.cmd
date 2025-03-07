@echo off

call 99-delete-all.cmd
call 01-create-configmap.cmd
call 02-create-secret.cmd
call 10-apply-pod.cmd
