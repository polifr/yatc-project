use rdkafka::producer::BaseProducer;

use crate::kafka::{common_configuration::create_common_configuration, event::TestEvent};

fn create_producer(bootstrap_servers: &str) -> BaseProducer {
    create_common_configuration(bootstrap_servers)
        .create()
        .expect("Errore in fase di creazione del producer Kafka.")
}
