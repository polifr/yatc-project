use opentelemetry::trace::TracerProvider;
use opentelemetry_sdk::trace::SdkTracerProvider;
use sqlx::{postgres::PgPoolOptions, Pool, Postgres, Row};
use tracing::{debug, info};
use tracing_opentelemetry::OpenTelemetryLayer;
use tracing_subscriber::layer::SubscriberExt;
use tracing_subscriber::util::SubscriberInitExt;

use tokio::join;
use tracing_subscriber::{EnvFilter, Layer};


use crate::config::Config;

mod config;
mod controller;
mod kafka;

fn init_tracing() {
    let tracer = SdkTracerProvider::builder().build().tracer("ms-tracer");
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
            .with_filter(EnvFilter::from("debug")))
            .with(otel_layer) // Aggiunge il livello OpenTelemetry
        .init();
}

async fn init_connection_pool(url: &str) -> Result<Pool<Postgres>, sqlx::error::Error> {
    info!("Connecting to pg: {}", url);

    // Creazione del pool
    // let pool = Pool::<Postgres>::connect(url).await?;
    let pool: Pool<Postgres> = PgPoolOptions::new()
            .max_connections(5)
            .connect(url)
            .await?.into();

    // Verifica della connessione
    let check: i32 = sqlx::query("SELECT 1").fetch_one(&pool).await?.get(0);
    debug!("Test della connessione: {}", check);

    Ok(pool)
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

    info!("Connecting to cache: {}", &config.cache_url);

    let connection_pool = init_connection_pool(&config.database_url);

    let consumer = kafka::consumer::consume_and_print(
        "yatc-kafka:9092",
        "ms-rust-axum", 
        &["yatc-test-topic"]
    );
    info!("Sottosistema kafka configurato.");

    let server = controller::test::create_controller().await;
    info!("Server Axum configurato.");

    let _ = join!(connection_pool, consumer, server);
    info!("Application started.");
}
