@echo off

call 99-delete-all
call 01-create-configmap
call 03-apply-rbac
call 10-apply-pod
