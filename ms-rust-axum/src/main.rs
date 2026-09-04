use std::sync::Arc;

use opentelemetry::trace::TracerProvider;
use opentelemetry_sdk::trace::SdkTracerProvider;
use tracing::{debug, info};
use tracing_opentelemetry::OpenTelemetryLayer;
use tracing_subscriber::layer::SubscriberExt;
use tracing_subscriber::util::SubscriberInitExt;

use tokio::join;
use tracing_subscriber::{EnvFilter, Layer};

use crate::{config::Config, controller::app_state::AppState, domain::{pool::connection_pool::init_connection_pool, repository::postgres_event_repository::PostgresEventRepository}, service::event_service::EventService};

mod config;
mod controller;
mod domain;
mod kafka;
mod service;

fn init_tracing() -> SdkTracerProvider {
    let exporter = opentelemetry_otlp::SpanExporter::builder()
        .with_tonic()
        .build()
        .expect("Failed to create OTLP exporter");
    let provider = SdkTracerProvider::builder()
        .with_batch_exporter(exporter)
        .build();

    let tracer = provider.tracer("ms-tracer");
    let otel_layer = OpenTelemetryLayer::new(tracer);

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
            .with_filter(EnvFilter::from("debug"))
        )
        .with(otel_layer) // Aggiunge il livello OpenTelemetry
        .init();

    provider
}

#[tokio::main]
async fn main() {
    let tracer_provider = init_tracing();

    info!("Starting...");

    debug!("Dotenv initializing...");
    let var_name = dotenv::from_filename(
        std::env::var("ENV_FILENAME").unwrap_or(".env.release".to_string())
    ).ok();
    debug!("Dotenv initialized: {}", &var_name.unwrap().display());

    let config = Config::init();

    info!("Connecting to cache: {}", &config.cache_url);

    let pool = init_connection_pool(&config.database_url).await;
    let event_repository = Arc::new(PostgresEventRepository::new(pool.clone()));
    let event_service = Arc::new(EventService::new(event_repository.clone()));
    let app_state = AppState { event_service };

    let consumer = kafka::consumer::consume_and_print(
        "yatc-kafka:9092",
        "ms-rust-axum", 
        &["yatc-test-topic"]
    );
    info!("Sottosistema kafka configurato.");

    let server = controller::api::create_controller(app_state).await;
    info!("Server Axum configurato.");

    let _ = join!(consumer, server);
    info!("Application started.");

    tracer_provider.shutdown().unwrap();
}
