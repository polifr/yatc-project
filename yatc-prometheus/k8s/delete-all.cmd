kubectl delete -f yatc-prometheus-deployment.yaml
kubectl delete -f yatc-prometheus-service.yaml
kubectl delete -f yatc-prometheus-pv.yaml
kubectl delete -f yatc-prometheus-claim.yaml
kubectl delete configmap yatc-prometheus-config
