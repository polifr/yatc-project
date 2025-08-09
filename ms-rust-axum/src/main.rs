use tracing::{debug, info};
use tracing_subscriber::layer::SubscriberExt;
use tracing_subscriber::util::SubscriberInitExt;

use tokio::join;
use tracing_subscriber::{EnvFilter, Layer};

use crate::config::Config;

mod config;
mod controller;
mod kafka;

fn init_tracing() {
    // let tracer = opentelemetry::trace::TracerProvider::default()
    //     .tracer("example-tracer");

    // let otel_layer = OpenTelemetryLayer::new(tracer);

    tracing_subscriber::registry()
        .with(
            tracing_subscriber::EnvFilter::try_from_default_env()
                .unwrap_or_else(|_| format!("{}=debug", env!("CARGO_CRATE_NAME")).into()),
        )
        .with(tracing_subscriber::fmt::layer()
            .json()
            .with_span_list(true)
            .with_current_span(true)
            .with_target(false)
            .with_level(true)
            .with_thread_ids(false)
            .with_thread_names(false)
            .with_filter(EnvFilter::from("debug")))
        // .with(otel_layer) // Aggiunge il livello OpenTelemetry
        .init();
}

#[tokio::main]
async fn main() {
    init_tracing();

    info!("Starting...");

    debug!("Dotenv initializing...");
    let var_name = dotenv::from_filename(
        std::env::var("ENV_FILENAME").unwrap_or(".env.release".to_string())
    ).ok();
    debug!("Dotenv initialized: {}", &var_name.unwrap().display());

    let config = Config::init();

    info!("Connecting to pg: {}", &config.database_url);
    info!("Connecting to cache: {}", &config.cache_url);

    let consumer = kafka::consumer::consume_and_print(
        "yatc-kafka:9092",
        "ms-rust-axum", 
        &["yatc-test-topic"]
    );
    info!("Sottosistema kafka configurato.");

    let server = controller::test::create_controller().await;
    info!("Server Axum configurato.");

    let _ = join!(consumer, server);
    info!("Application started.");
}
