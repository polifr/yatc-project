#!/bin/sh

./../k8s/99-delete-all.sh
./09-build-native-image.sh
./../k8s/10-apply-pod.sh
