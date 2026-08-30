use sqlx::PgPool;
use async_trait::async_trait;

use crate::domain::{entity::event::Event, repository::event_repository::EventRepository};

#[derive(Clone)]
pub struct PostgresEventRepository {
    pool: PgPool,
}

impl PostgresEventRepository {
    pub fn new(pool: PgPool) -> Self {
        Self { pool }
    }
}

#[async_trait]
impl EventRepository for PostgresEventRepository {
    async fn find_all(&self) -> Result<Vec<Event>, sqlx::Error> {
        sqlx::query_as::<_, Event>("SELECT id, message FROM t_event")
                .fetch_all(&self.pool)
                .await
    }

    async fn find_by_id(&self, id: i64) -> Result<Option<Event>, sqlx::Error> {
        sqlx::query_as::<_, Event>("SELECT id, message FROM t_event WHERE id = $1")
                .bind(id)
                .fetch_optional(&self.pool)
                .await
    }
}
