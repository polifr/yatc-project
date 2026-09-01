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

    pub async fn find_by_id(&self, id: i64) -> Result<Option<Event>, ServiceError> {
        Ok(self.event_repository.find_by_id(id).await?)
    }

    pub async fn save(&self, event: &Event) -> Result<Event, ServiceError> {
        Ok(self.event_repository.save(event).await?)
    }

    pub async fn update(&self, event: &Event) -> Result<Option<Event>, ServiceError> {
        Ok(self.event_repository.update(event).await?)
    }

    pub async fn delete_by_id(&self, id: i64) -> Result<Option<i64>, ServiceError> {
        Ok(self.event_repository.delete_by_id(id).await?)
    }

}
