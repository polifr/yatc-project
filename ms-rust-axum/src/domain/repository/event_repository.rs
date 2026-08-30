use async_trait::async_trait;

use crate::domain::entity::event::Event;

#[async_trait]
pub trait EventRepository: Send + Sync {
    async fn find_all(&self) -> Result<Vec<Event>, sqlx::Error>;
    async fn find_by_id(&self, id: i64) -> Result<Option<Event>, sqlx::Error>;
}
