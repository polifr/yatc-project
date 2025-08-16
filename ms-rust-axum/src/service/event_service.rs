use sqlx::Database;

use crate::domain::repository::event_repository::EventRepository;

pub struct EventService {
    event_repository: EventRepository,
}

impl EventService {
    pub fn new(event_repository: EventRepository) -> Self {
        Self { event_repository }
    }
}
