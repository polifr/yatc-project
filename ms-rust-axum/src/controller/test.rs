use axum::{
    extract,
    extract::State,
    middleware,
    response::IntoResponse,
    routing::get, 
    serve::Serve, 
    Router,
};
use tower_http::trace::TraceLayer;
use tracing::{info, span, Level};
use uuid::Uuid;

#[derive(Clone)]
struct AppState;

async fn hello(State(_state): State<AppState>) -> impl IntoResponse {
    info!("Calling hello fn...");
    "Hello, Rust! This is a microservice!"
}

pub async fn create_controller() -> Serve<tokio::net::TcpListener, Router, Router> {
    info!("Configurazione server Axum...");
    let app = Router::new()
            .route("/api/rust-axum/test/v1", get(hello))
            .with_state(AppState)
            .layer(TraceLayer::new_for_http())
            .layer(middleware::from_fn(tracing_middleware));
    let listener = tokio::net::TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app)
}

async fn tracing_middleware(
    req: extract::Request,
    next: axum::middleware::Next,
) -> impl IntoResponse {
    let new_req = req;
    let headers = new_req.headers();

    let (trace_id, parent_span_id) = headers
        .get("traceparent")
        .and_then(|hv| hv.to_str().ok())
        .and_then(parse_traceparent)
        .unwrap_or_else(|| {
            // Se non esiste header, generiamo un nuovo trace_id
            let trace_id = Uuid::new_v4().simple().to_string();
            (trace_id.clone(), "0000000000000000".to_string()) // parent_span_id nullo
        });

    // Generiamo un nuovo span_id per lo span corrente
    let span_id = Uuid::new_v4().as_u128(); // prendiamo 16 byte (come spanId)
    let span_id_hex = format!("{:016x}", span_id & 0xFFFFFFFFFFFFFFFF); // solo ultimi 64 bit

    // Creazione dello span figlio
    let span = span!(
        Level::INFO,
        "incoming_request",
        trace_id = %trace_id,
        parent_span_id = %parent_span_id,
        span_id = %span_id_hex
    );

    let response = span.in_scope(|| async {
        info!("span created for incoming request with trace {trace_id:?}");
        next.run(new_req).await
    })
    .await;

    response
}

/// Parsing W3C traceparent header: version-traceid-spanid-flags
fn parse_traceparent(header: &str) -> Option<(String, String)> {
    let parts: Vec<&str> = header.split('-').collect();
    if parts.len() == 4 {
        let trace_id = parts[1].to_string();
        let span_id = parts[2].to_string();
        Some((trace_id, span_id))
    } else {
        None
    }
}
