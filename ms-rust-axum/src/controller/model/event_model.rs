use serde::Serialize;

use crate::domain::entity::event::Event;

#[derive(Debug, Serialize)]
pub struct EventModel {
    pub id: i64,
    pub message: String,
}

impl From<Event> for EventModel {
    fn from(event: Event) -> Self {
        Self {
            id: event.id,
            message: event.message,
        }
    }
}
