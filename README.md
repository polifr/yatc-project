# yatc-project
Yet Another Test Cluster Project - an umbrella for a number of clustered subprojects

## Modules and Features
- K8S deployment files (`k8s` main directory for cluster creation, configuration and tunneling, and in each project subdirs for image building and deployment) to be used on a minikube local instance
- KeyCloak Oauth2 Identity Provider [yatc-keycloak](./yatc-keycloak) with predefined `yatc-realm` realm
- Postgresql [yatc-postgres](./yatc-postgres) and MongoDB [yatc-mongo](./yatc-mongo) databases
- Redis cache [yayc-redis](./yatc-redis)
- Kafka message broker (without ZooKeeper) [yatc-kafka](./yatc-kafka)
- Prometheus monitoring with service discovery [yatc-prometheus](./yatc-prometheus) and Grafana [yatc-grafana](./yatc-grafana); integration with metrics data endpoints and exporters
- Log monitoring with Alloy [yatc-alloy](./yatc-alloy) and Loky [yatc-loki](./yatc-loki), integrated with Grafana, inspired by:
  - https://github.com/docker/awesome-compose/tree/master/prometheus-grafana 
  - https://medium.com/@ahmadbilalch891/how-to-set-up-grafana-loki-and-prometheus-locally-with-docker-compose-part-1-of-3-62fb25e51d92
- Spring API Gateway [gw-spring-cloud](./gw-spring-cloud), based on reactive stack, integrated with KeyCLoak for authentication and Redis for session caching
- Spring servlet [ms-spring-servlet](./ms-spring-servlet) and reactive [ms-spring-reactive](./ms-spring-reactive) based microservices
- RUST Axum microservice [ms-rust-axum](./ms-rust-axum)
- NodeJS Express microservice [ms-node-express](./ms-node-express) - Work in Progress
- Propagation of traces (`trace_id`) using W3C standards (i.e. `traceparent` header) in http and kafka communications

## References

See:
- https://github.com/docker/awesome-compose

## TODO
- Setup Rust ms to read environment variables, and integration (rest api, database, security, kafka, etc)
- Add some Go ms (full integration as Rust) -> [ms-go-fiber](./ms-go-fiber) and [ms-go-gin](./ms-go-gin)
- Add some Java (Quarkus) ms (full integration as Rust) -> [ms-quarkus-jee](./ms-quarkus-jee) and [ms-quarkus-munity](./ms-quarkus-munity)
- Add some Python ms
- Add some Scala ms
- Add some Nginx server with a basic frontends (Angular & Material / React & MUI / Vue & ?) -> [fe-angular-material](./fe-angular-material) and [fe-react-mui](./fe-react-mui)
- Activate OpenAPI / Swagger / Scalar API browsing through a backend gateway / ingress (i.e. using Swagger or Scalar - see [here](https://www.linkedin.com/pulse/scalar-now-officially-supported-spring-boot-shane-o-connor-9ni3e/?trackingId=SiSAPNi1wZNdsNBqwmjZ6g%3D%3D)); transform the openapi gateway routes using wildcards and expressions
- Create and persist Grafana dashboards
- Manage `USER` directive in `Dockerfile`s to avoid run as `root`
- Change `docker-compose.yaml` for rely on module's `Dockerfile`s and run builds accordingly
- Add a SonarQube (https://hub.docker.com/_/sonarqube) and / or a bundled OWASP Dependency Tracker (https://hub.docker.com/r/dependencytrack/bundled) for project scanning

## Not here, not now:
- Include a Dockerfile.k8s.graalvm for java based microservices: tested, but not good to be tested on this infrastructure, because huge image building times and bad dependency compatibility in Spring Session.
- Add Camunda Zeebe (v8) workflow engine for orchestration (see https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/): too many components to be put in the cluster, can't do this on a simple pc. At most, I would try to use Camunda Workflow Engine 7...
