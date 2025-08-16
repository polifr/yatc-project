use sqlx::prelude::FromRow;

#[derive(Debug, FromRow)]
pub struct Event {
    id: i64,
    message: String,
}
