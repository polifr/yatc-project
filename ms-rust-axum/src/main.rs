use axum::{Router, routing::get};

async fn hello() -> &'static str {
    "Hello, Rust! This is a microservice!"
}

#[tokio::main]
async fn main() {
    let app = Router::new().route("/", get(hello));
    let listener = tokio::net::TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}
