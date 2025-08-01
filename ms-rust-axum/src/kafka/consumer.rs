use rdkafka::config::{ClientConfig, RDKafkaLogLevel};
use rdkafka::consumer::stream_consumer::StreamConsumer;
use rdkafka::consumer::{CommitMode, Consumer};
use rdkafka::message::{Headers, Message};
use tracing::{info, warn};

use tokio_stream::StreamExt;


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

pub async fn consume_and_print(bootstrap_servers: &str, group_id: &str, topics: &[&str]) {

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

/*
fn decode_event(msg: &BorrowedMessage) -> Result<TestEvent, Box<dyn std::error::Error>> {
    let b64_str = msg.payload_view::<str>()
        .ok_or("Nessun payload")??;

    let decoded_bytes = base64::engine::general_purpose::STANDARD.decode(b64_str)?;
    let json_str = std::str::from_utf8(&decoded_bytes)?;
    let event = serde_json::from_str::<TestEvent>(json_str)?;

    Ok(event)
}
*/
