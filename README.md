# yatc-project
Yet Another Test Cluster Project - an umbrella for a number of clustered subprojects

## Features and modules
- KeyCloak Oauth2 Identity Provider [yatc-keycloak](./yatc-keycloak)
- Postgresql database [yatc-postgresql](./yatc-postgresql)
- Redis cache
- Spring API Gateway [gw-spring-cloud](./gw-spring-cloud)

## References

See:
- https://github.com/docker/awesome-compose

## TODO
- Setup Rust ms to read environment variables, and integration (rest api, database, security, kafka, etc)
- Add Kafka (latest without ZooKeeper)
- Add Prometheus / Grafana / Loki (see https://github.com/docker/awesome-compose/tree/master/prometheus-grafana or https://medium.com/@ahmadbilalch891/how-to-set-up-grafana-loki-and-prometheus-locally-with-docker-compose-part-1-of-3-62fb25e51d92)
- Add some Go ms (full integration as Rust)
- Add some Java (Spring / Quarkus) ms (full integration as Rust)
- Add some Node.js ms (full integration as Rust) see https://nodejs.org/en/learn/getting-started/introduction-to-nodejs
- Add some Nginx server with a basic frontends (Angular & Material / React & MUI / Vue & ?)
- Check how traces are propagated through ms calls (sync / async)
- Add TimeZone managing in `Dockerfile`s
- Manage `USER` directive in `Dockerfile`s to avoid run as `root`
- Setup `yatc-realm` with clients (e.g. the API Gateways) and pre defined users for authentication
- Differentiate single and multi stage `Dockerfile`s, create build scripts and set image name
- Change `docker-compose.yaml` for rely on module's `Dockerfile`s and run builds accordingly
- Add Camunda Zeebe (v8) workflow engine for orchestration (see https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/)
- Add K8S deployment files to be used on a minikube local instance
