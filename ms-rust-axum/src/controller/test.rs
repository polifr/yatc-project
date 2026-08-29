use axum::{
    extract::State,
    response::IntoResponse,
    routing::get, 
    Router,
};
use tracing::{info};

use crate::controller::app_state::AppState;

async fn hello(State(_state): State<AppState>) -> impl IntoResponse {
    info!("Calling hello fn...");
    "Hello, Rust! This is a microservice!"
}

pub fn routes() -> Router<AppState> {
    Router::new()
        .route("/api/rust-axum/test/v1", get(hello))
}
