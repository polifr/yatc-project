use tracing::{info};

use axum::{routing::get, serve::Serve, Router};

async fn hello() -> &'static str {
    "Hello, Rust! This is a microservice!"
}

pub async fn create_controller() -> Serve<tokio::net::TcpListener, Router, Router> {
    info!("Configurazione server Axum...");
    let app = Router::new().route("/api/rust-axum/test/v1", get(hello));
    let listener = tokio::net::TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app)
}
