use std::sync::Arc;

use crate::{domain::repository::postgres_event_repository::PostgresEventRepository, service::event_service::EventService};

#[derive(Clone)]
pub struct AppState {
    pub event_service: Arc<EventService<PostgresEventRepository>>,
}
