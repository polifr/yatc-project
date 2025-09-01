# yatc-kafka

Message broker Kafka; si utilizza un'immagine ufficiale (https://hub.docker.com/r/apache/kafka) a cui si integra il jar del jmx exporter (https://github.com/prometheus/jmx_exporter), che viene eseguito come java agent dal processo principale e che espone le metriche in formato Prometheus sulla porta 5556.
