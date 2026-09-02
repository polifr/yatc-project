use axum::{
    serve::Serve, 
    Router,
};

use opentelemetry::propagation::TextMapPropagator;
use opentelemetry_http::HeaderExtractor;
use opentelemetry_sdk::propagation::TraceContextPropagator;

use tower_http::trace::TraceLayer;

use tracing::{info, info_span};
use tracing_opentelemetry::OpenTelemetrySpanExt;

use crate::controller::{app_state::AppState, event, test};

pub async fn create_controller(state: AppState) -> Serve<tokio::net::TcpListener, Router, Router> {
    info!("Configurazione server Axum...");
    let app = Router::new()
            .merge(test::routes())
            .nest("/api/rust-axum/event", event::routes())
            .with_state(state)
            .layer(
                TraceLayer::new_for_http()
                .make_span_with(|request: &axum::http::Request<_>| {
                    let propagator = TraceContextPropagator::new();

                    let parent_context = propagator.extract(
                        &HeaderExtractor(request.headers())
                    );

                    let span = tracing::info_span!(
                        "http_request",
                        method = %request.method(),
                        uri = %request.uri(),
                    );

                    let _ = span.set_parent(parent_context);

                    span
                })
            );
    let listener = tokio::net::TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app)
}
