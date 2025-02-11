# yatc-project
Yet Another Test Cluster Project - an umbrella for a number of clustered subprojects

## Features and modules
- KeyCloak Oauth2 Identity Provider [yatc-keycloak](./yatc-keycloak)
- Postgresql database [yatc-postgresql](./yatc-postgresql)
- Redis cache
- Spring API Gateway [gw-spring-cloud](./gw-spring-cloud)

## TODO
- Setup Rust ms to read environment variables, and integration (rest api, database, security, kafka, etc)
- Add Kafka (latest without ZooKeeper)
- Add Prometheus / Grafana (see https://github.com/docker/awesome-compose/tree/master/prometheus-grafana or https://medium.com/@ahmadbilalch891/how-to-set-up-grafana-loki-and-prometheus-locally-with-docker-compose-part-1-of-3-62fb25e51d92)
- Add ELK stack (see https://github.com/docker/awesome-compose/tree/master/elasticsearch-logstash-kibana)
- Add some Go ms (full integration as Rust)
- Add some Java ms (full integration as Rust)
- Add some Node.js ms (full integration as Rust) see https://nodejs.org/en/learn/getting-started/introduction-to-nodejs
- Add a Nginx server with a basic Angular (Material) frontend
- Check how traces are propagated through ms calls (sync / async)
