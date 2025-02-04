# yatc-project
Yet Another Test Cluster Project - an umbrella for a number of clustered subprojects


**TODO**
- Setup Rust ms to read environment variables, and integration (rest api, database, security, kafka, etc)
- Add Redis
- Add Postgres
- Add Kafka (latest without ZooKeeper)
- Add Prometheus / Grafana (see https://github.com/docker/awesome-compose/tree/master/prometheus-grafana or https://medium.com/@ahmadbilalch891/how-to-set-up-grafana-loki-and-prometheus-locally-with-docker-compose-part-1-of-3-62fb25e51d92)
- Add ELK stack (see https://github.com/docker/awesome-compose/tree/master/elasticsearch-logstash-kibana)
- Add some Go ms (full integration as Rust)
- Add some Java ms (full integration as Rust)
- Add an Oauth2 provider (lighter than KeyCloak? see https://github.com/networknt/light-oauth2 but seems archived)
- Add an Api Gateway (Spring Cloud GW? Or more than one, to check different solutions...)
- Add a Nginx server with a basic Angular (Material) frontend
- Check how traces are propagated through ms calls (sync / async)
