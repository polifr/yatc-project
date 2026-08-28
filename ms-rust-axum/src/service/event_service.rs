use crate::domain::repository::event_repository::EventRepository;
use crate::domain::entity::event::Event;

pub struct EventService<R>
where R: EventRepository {
    event_repository: R,
}

impl<R> EventService<R>
where R: EventRepository {
    pub fn new(event_repository: R) -> Self {
        Self { event_repository }
    }

    pub async fn find_all(&self) -> Result<Vec<Event>, sqlx::Error> {
        self.event_repository.find_all().await
    }
}
