use sqlx::Database;

use crate::domain::repository::event_repository::EventRepository;

pub struct EventService<DB: Database> {
    event_repository: EventRepository<DB>,
}

impl<DB: Database> EventService<DB> {
    pub fn new(event_repository: EventRepository<DB>) -> Self {
        Self { event_repository }
    }
}
