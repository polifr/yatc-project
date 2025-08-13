use sqlx::{Database, Pool};

pub struct EventRepository<DB: Database> {
    pool: Pool<DB>,
}

impl<DB: Database> EventRepository<DB> {
    pub fn new(pool: Pool<DB>) -> Self {
        Self { pool }
    }
}
