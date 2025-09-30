# ms-node-express

Microservizio in Node.js basato sul framework Express.

## Riferimenti:

### Rest con Node.js
- https://www.cloud.it/tutorial/come-creare-rest-api-con-express-nodejs.aspx
- https://blog.postman.com/how-to-create-a-rest-api-with-node-js-and-express/
- https://www.toptal.com/nodejs/secure-rest-api-in-nodejs

### Multi stage Dockerfile per Node.js
- https://sachithsiriwardana.medium.com/dockerizing-nodejs-application-with-multi-stage-build-e30477ca572

### TODO
- Configurare il deployment del servizio su k8s
- Esporre le metriche Prometheus tramite exporter, riferimenti:
  - https://grafana.com/docs/grafana-cloud/monitor-applications/asserts/enable-prom-metrics-collection/runtimes/nodejs/
  - https://github.com/siimon/prom-client
  - https://grafana.com/oss/prometheus/exporters/nodejs-exporter/
- Includere l'esecuzione di owasp-dependency-check: https://www.npmjs.com/package/owasp-dependency-check
