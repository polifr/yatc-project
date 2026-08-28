use sqlx::prelude::FromRow;

#[derive(Debug, FromRow)]
pub struct Event {
    pub id: i64,
    pub message: String,
}
