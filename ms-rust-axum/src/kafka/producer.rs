use rdkafka::{config::RDKafkaLogLevel, producer::BaseProducer, ClientConfig};
use rdkafka::util::get_rdkafka_version;
use tracing::{info};

use crate::kafka::{common_configuration::create_common_configuration, event::TestEvent};

fn create_producer(bootstrap_servers: &str) -> BaseProducer {
    create_common_configuration(bootstrap_servers)
        .create()
        .expect("Errore in fase di creazione del producer Kafka.")
}
