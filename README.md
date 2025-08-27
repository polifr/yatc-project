# yatc-project
Yet Another Test Cluster Project - an umbrella for a number of clustered subprojects

## Features and modules
- KeyCloak Oauth2 Identity Provider [yatc-keycloak](./yatc-keycloak) with predefined `yatc-realm` realm
- Postgresql database [yatc-postgres](./yatc-postgres)
- Redis cache [yayc-redis](./yatc-redis)
- Kafka message broker (latest, without ZooKeeper) [yatc-kafka](./yatc-kafka)
- Prometheus monitoring with service discovery [yatc-prometheus](./yatc-prometheus) and Grafana [yatc-grafana](./yatc-grafana); integration with metrics data exporters
- Log monitoring with Alloy [yatc-alloy](./yatc-alloy) and Loky [yatc-loki](./yatc-loki), integrated with Grafana (inspired by https://github.com/docker/awesome-compose/tree/master/prometheus-grafana and https://medium.com/@ahmadbilalch891/how-to-set-up-grafana-loki-and-prometheus-locally-with-docker-compose-part-1-of-3-62fb25e51d92)
- Spring API Gateway [gw-spring-cloud](./gw-spring-cloud), based on reactive stack, integrated with KeyCLoak for authentication
- Spring servlet and reactive based microservices
- K8S deployment files (`k8s` main directory - cluster creation and configuration - and in each project subdirs - image building and deployment) to be used on a minikube local instance
- Propagation of traces (`trace_id`) using W3C standards (i.e. `traceparent` header) in http and kafka communications

## References

See:
- https://github.com/docker/awesome-compose

## TODO
- Setup Rust ms to read environment variables, and integration (rest api, database, security, kafka, etc)
- Add some Go ms (full integration as Rust)
- Add some Java (Quarkus) ms (full integration as Rust)
- Add some Node.js ms (full integration as Rust) see https://nodejs.org/en/learn/getting-started/introduction-to-nodejs
- Add some Nginx server with a basic frontends (Angular & Material / React & MUI / Vue & ?)
- Manage `USER` directive in `Dockerfile`s to avoid run as `root`
- Differentiate single and multi stage `Dockerfile`s, create build scripts and set image name
- Change `docker-compose.yaml` for rely on module's `Dockerfile`s and run builds accordingly
- Add Camunda Zeebe (v8) workflow engine for orchestration (see https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/)
- Include a Dockerfile.k8s.graalvm for java based microservices
