use std::sync::Arc;

use crate::domain::repository::event_repository::EventRepository;
use crate::domain::entity::event::Event;
use crate::service::service_error::ServiceError;

pub struct EventService<R>
where R: EventRepository {
    event_repository: Arc<R>,
}

impl<R> EventService<R>
where R: EventRepository {
    pub fn new(event_repository: Arc<R>) -> Self {
        Self { event_repository }
    }

    pub async fn find_all(&self) -> Result<Vec<Event>, ServiceError> {
        Ok(self.event_repository.find_all().await?)
    }
}
