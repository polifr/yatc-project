use rdkafka::config::{ClientConfig, RDKafkaLogLevel};
use rdkafka::util::get_rdkafka_version;
use tracing::{debug};

pub fn create_common_configuration(bootstrap_servers: &str) -> ClientConfig {
    debug!("Configurazione kafka...");
    let (version_n, version_s) = get_rdkafka_version();
    debug!("rd_kafka_version: 0x{:08x}, {}", version_n, version_s);

    ClientConfig::new()
        .set("bootstrap.servers", bootstrap_servers)
        .set("enable.partition.eof", "false")
        .set("session.timeout.ms", "6000")
        .set("enable.auto.commit", "true")
        .set("auto.offset.reset", "earliest")
        .set_log_level(RDKafkaLogLevel::Debug).to_owned()
}
