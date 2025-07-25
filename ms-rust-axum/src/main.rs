use base64::{engine::general_purpose, Engine as _};
use rdkafka::config::{ClientConfig, RDKafkaLogLevel};
use rdkafka::consumer::stream_consumer::StreamConsumer;
use rdkafka::consumer::{CommitMode, Consumer};
use rdkafka::message::{BorrowedMessage, Headers, Message};
use rdkafka::producer::BaseProducer;
use rdkafka::util::get_rdkafka_version;

use tracing::{debug, info, warn};
use tracing_subscriber::layer::SubscriberExt;
use tracing_subscriber::util::SubscriberInitExt;

use tokio::join;
use tokio_stream::StreamExt;

use crate::config::Config;

use axum::{Router, routing::get};

use serde::{Deserialize, Serialize};

mod config;

#[derive(Debug, Deserialize, Serialize)]
struct TestEvent {
    message: String,
    creation_time: String,
}

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
        .set("auto.offset.reset", "earliest")
        .set_log_level(RDKafkaLogLevel::Debug)
        .create()
        .expect("Errore in fase di creazione del consumer Kafka.")
}

async fn consume_and_print(bootstrap_servers: &str, group_id: &str, topics: &[&str]) {

    let consumer = create_consumer(bootstrap_servers, group_id);

    consumer
        .subscribe(&topics.to_vec())
        .expect("Errore in fase di sottoscrizione ai topic.");

    let mut message_stream = consumer.stream();
    info!("Kafka consumer started...");

    while let Some(message_result) = message_stream.next().await {
        match message_result {
            Err(e) => warn!("Kafka error: {}", e),
            Ok(m) => {
                let payload = match m.payload_view::<str>() {
                    None => "",
                    Some(Ok(s)) => s,
                    Some(Err(e)) => {
                        warn!("Error while deserializing message payload: {e:?}");
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

/*/
fn decode_event(msg: &BorrowedMessage) -> Result<TestEvent, Box<dyn std::error::Error>> {
    let b64_str = msg.payload_view::<str>()
        .ok_or("Nessun payload")??;

    let decoded_bytes = base64::engine::general_purpose::STANDARD.decode(b64_str)?;
    let json_str = std::str::from_utf8(&decoded_bytes)?;
    let event = serde_json::from_str::<TestEvent>(json_str)?;

    Ok(event)
}
*/

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

    info!("Configurazione sottosistema kafka...");
    let (version_n, version_s) = get_rdkafka_version();
    info!("rd_kafka_version: 0x{:08x}, {}", version_n, version_s);
    
    let consumer = consume_and_print(
        "yatc-kafka:9092",
        "ms-rust-axum", 
        &["yatc-test-topic"]
    );
    info!("Sottosistema kafka configurato.");

    info!("Configurazione server Axum...");
    let app = Router::new().route("/api/rust-axum/test/v1", get(hello));
    let listener = tokio::net::TcpListener::bind("0.0.0.0:8080").await.unwrap();
    let server = axum::serve(listener, app);
    info!("Server Axum configurato.");

    let _ = join!(consumer, server);
    info!("Application started.");
}
