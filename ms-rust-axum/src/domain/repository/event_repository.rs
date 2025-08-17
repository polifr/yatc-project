use sqlx::{Pool, Postgres};

use crate::domain::model::event::Event;

#[derive(Clone)]
pub struct EventRepository {
    pool: Pool<Postgres>,
}

impl EventRepository {
    pub fn new(pool: Pool<Postgres>) -> Self {
        Self { pool }
    }

    pub async fn find_all(&self) -> Result<Vec<Event>, sqlx::Error> {
        let events = sqlx::query_as::<_, Event>("SELECT id, message FROM t_event")
                .fetch_all(&self.pool)
                .await?;
        Ok(events)
    }
}
