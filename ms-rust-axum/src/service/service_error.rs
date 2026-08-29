pub enum ServiceError {
    Database(sqlx::Error),
}

impl From<sqlx::Error> for ServiceError {
    fn from(error: sqlx::Error) -> Self {
        ServiceError::Database(error)
    }
}
