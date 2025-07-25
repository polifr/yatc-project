use rdkafka::{config::RDKafkaLogLevel, producer::BaseProducer, ClientConfig};

use crate::kafka::event::TestEvent;

fn create_producer(bootstrap_servers: &str) -> BaseProducer {
    ClientConfig::new()
        .set("bootstrap.servers", bootstrap_servers)
        .set("enable.partition.eof", "false")
        .set("session.timeout.ms", "6000")
        .set("enable.auto.commit", "true")
        .set("auto.offset.reset", "earliest")
        .set_log_level(RDKafkaLogLevel::Debug)
        .create()
        .expect("Errore in fase di creazione del producer Kafka.")
}
