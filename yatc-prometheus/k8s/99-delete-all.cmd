kubectl delete service yatc-prometheus
kubectl delete deployment yatc-prometheus
@rem kubectl delete -f yatc-prometheus-pv.yaml
@rem kubectl delete -f yatc-prometheus-claim.yaml
kubectl delete clusterrolebinding yatc-prometheus
kubectl delete serviceaccount yatc-prometheus
kubectl delete configmap yatc-prometheus-config
