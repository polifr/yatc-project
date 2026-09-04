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

    #[tracing::instrument(skip(self))]
    async fn find_all(&self) -> Result<Vec<Event>, sqlx::Error> {
        sqlx::query_as::<_, Event>("SELECT id, message FROM t_event")
                .fetch_all(&self.pool)
                .await
    }

    #[tracing::instrument(skip(self), fields(event_id = id))]
    async fn find_by_id(&self, id: i64) -> Result<Option<Event>, sqlx::Error> {
        sqlx::query_as::<_, Event>("SELECT id, message FROM t_event WHERE id = $1")
                .bind(id)
                .fetch_optional(&self.pool)
                .await
    }

    #[tracing::instrument(skip(self, event))]
    async fn save(&self, event: &Event) -> Result<Event, sqlx::Error> {
        sqlx::query_as::<_, Event>(
                r#"
                INSERT into t_event (message)
                VALUES ($1)
                RETURNING
                    id,
                    message
                "#)
                .bind(&event.message)
                .fetch_one(&self.pool)
                .await
    }

    #[tracing::instrument(skip(self, event), fields(event_id = event.id))]
    async fn update(&self, event: &Event) -> Result<Option<Event>, sqlx::Error> {
        sqlx::query_as::<_, Event>(
                r#"
                UPDATE t_event
                SET message = $2
                WHERE id = $1
                RETURNING
                    id,
                    message
                "#)
                .bind(&event.id)
                .bind(&event.message)
                .fetch_optional(&self.pool)
                .await
    }

    #[tracing::instrument(skip(self), fields(event_id = id))]
    async fn delete_by_id(&self, id: i64) -> Result<Option<i64>, sqlx::Error> {
        sqlx::query_scalar::<_, i64>(
                r#"
                DELETE t_event
                WHERE id = $1
                RETURNING id
                "#)
                .bind(id)
                .fetch_optional(&self.pool)
                .await
    }
}
