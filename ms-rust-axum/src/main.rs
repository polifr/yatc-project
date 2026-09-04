use std::sync::Arc;

use tracing::{debug, info};

use tokio::join;

use crate::{config::Config, controller::app_state::AppState, domain::{pool::connection_pool::init_connection_pool, repository::postgres_event_repository::PostgresEventRepository}, service::event_service::EventService};

mod config;
mod controller;
mod domain;
mod kafka;
mod service;
mod tracer;

#[tokio::main]
async fn main() {
    let tracer_provider = tracer::init_tracing();

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
