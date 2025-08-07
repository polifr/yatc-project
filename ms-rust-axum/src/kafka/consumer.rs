use rdkafka::config::{ClientConfig, RDKafkaLogLevel};
use rdkafka::consumer::stream_consumer::StreamConsumer;
use rdkafka::consumer::{CommitMode, Consumer};
use rdkafka::message::{BorrowedHeaders, BorrowedMessage, Headers, Message};
use tracing::{debug, info, warn};

use base64::Engine;

use tokio_stream::StreamExt;

use crate::kafka::event::TestEvent;

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
                let traceparent_value = get_traceparent_header_value(m.headers());
                info!("Header traceparent value: {}", traceparent_value.unwrap_or_default());
                // TODO Attivare lo span del tracing con il traceparent estratto qui

                let mut json = String::new();
                match decode_event(&m) {
                    Ok(s) => {
                        info!("Messaggio deserializzato: {}", s);
                        json = s;
                    },
                    Err(e) => {
                        warn!("Error while deserializing message payload: {}", e);
                    },
                }
                let payload = &json[..];
                info!("key: '{:?}', payload: '{}', topic: {}, partition: {}, offset: {}, timestamp: {:?}",
                      m.key(), payload, m.topic(), m.partition(), m.offset(), m.timestamp());
                if let Some(headers) = m.headers() {
                    for header in headers.iter() {
                        info!("  Header {:#?}: {:?}", header.key, 
                                header.value.map(|h| String::from_utf8(h.to_vec()).unwrap_or_default()).unwrap_or_default());
                    }
                }
                consumer.commit_message(&m, CommitMode::Async).unwrap();
            }
        };
    }
}

fn decode_event(msg: &BorrowedMessage) -> Result<String, Box<dyn std::error::Error>> {
    let b64_str = msg.payload_view::<str>()
        .ok_or("Nessun payload")??;

    debug!("Raw payload received: {}", b64_str);
    // Si rimuovono eventuali virgolette dal messaggio - da verificare il motivo della presenza
    let b64_str_filtered = b64_str.replace(&['\"','\\','\''][..], "");

    debug!("Decoding base64 string {}", b64_str_filtered);
    let decoded_bytes = base64::engine::general_purpose::STANDARD.decode(b64_str_filtered)?;
    let json = String::from_utf8(decoded_bytes)?;

    debug!("Deserializing json event {}", json);
    let event = serde_json::from_str::<TestEvent>(&json)?;
    debug!("Event decoded: {}", event.to_string());

    Ok(json)
}

fn get_traceparent_header_value(message_headers: Option<&BorrowedHeaders>) -> Option<String> {
    // TODO Ottimizzare la routine di estrazione
    if let Some(headers) = message_headers {
        for header in headers.iter() {
            if header.key == "traceparent" {
                header.value.map(|h| String::from_utf8(h.to_vec()).unwrap_or_default());
            }
        }
    }

    None
}