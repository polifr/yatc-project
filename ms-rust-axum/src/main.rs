use rdkafka::{config::RDKafkaLogLevel, consumer::{Consumer, CommitMode, StreamConsumer}, util::get_rdkafka_version, ClientConfig, Message, message::Headers};
use tracing::{debug, info, warn};
use tracing_subscriber::layer::SubscriberExt;
use tracing_subscriber::util::SubscriberInitExt;
use crate::config::Config;

use axum::{Router, routing::get};

mod config;

async fn hello() -> &'static str {
    "Hello, Rust! This is a microservice!"
}

fn create_consumer(bootstrap_servers: &str, group_id: &str) -> StreamConsumer {
    ClientConfig::new()
        .set("bootstrap.servers", bootstrap_servers)
        .set("group.id", group_id)
        .set("enable.partition.eof", "false")
        .set("session.timeout.ms", "6000")
        .set("enable.auto.commit", "true")
        .set_log_level(RDKafkaLogLevel::Debug)
        .create()
        .expect("Errore in fase di creazione del client Kafka.")
}

async fn consume_and_print(bootstrap_servers: &str, group_id: &str, topics: &[&str]) {

    let consumer = create_consumer(bootstrap_servers, group_id);

    consumer
        .subscribe(&topics.to_vec())
        .expect("Errore in fase di sottoscrizione ai topic.");

    loop {
        match consumer.recv().await {
            Err(e) => warn!("Kafka error: {}", e),
            Ok(m) => {
                let payload = match m.payload_view::<str>() {
                    None => "",
                    Some(Ok(s)) => s,
                    Some(Err(e)) => {
                        warn!("Error while deserializing message payload: {:?}", e);
                        ""
                    }
                };
                info!("key: '{:?}', payload: '{}', topic: {}, partition: {}, offset: {}, timestamp: {:?}",
                      m.key(), payload, m.topic(), m.partition(), m.offset(), m.timestamp());
                if let Some(headers) = m.headers() {
                    for header in headers.iter() {
                        info!("  Header {:#?}: {:?}", header.key, header.value);
                    }
                }
                consumer.commit_message(&m, CommitMode::Async).unwrap();
            }
        };
    }
}

#[tokio::main]
async fn main() {
    tracing_subscriber::registry()
        .with(
            tracing_subscriber::EnvFilter::try_from_default_env()
                .unwrap_or_else(|_| format!("{}=debug", env!("CARGO_CRATE_NAME")).into()),
        )
        .with(tracing_subscriber::fmt::layer())
        .init();

    info!("Starting...");

    debug!("Dotenv initializing...");
    let var_name = dotenv::from_filename(
        std::env::var("ENV_FILENAME").unwrap_or(".env.release".to_string())
    ).ok();
    debug!("Dotenv initialized: {}", &var_name.unwrap().display());

    let config = Config::init();

    info!("Connecting to pg: {}", &config.database_url);
    info!("Connecting to cache: {}", &config.cache_url);

    let app = Router::new().route("/api/rust-axum/test/v1", get(hello));
    let listener = tokio::net::TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app).await.unwrap();

    let (version_n, version_s) = get_rdkafka_version();
    info!("rd_kafka_version: 0x{:08x}, {}", version_n, version_s);
    
    consume_and_print(
        "yatc-kafka:9092",
        "ms-rust-axum", 
        &["yatc-test-topic"]
    ).await;

    info!("Application started.");
}
