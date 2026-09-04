use sqlx::{Pool, Postgres, Row, pool::PoolOptions};
use tracing::{debug, info};

pub async fn init_connection_pool(db_url: &str) -> Pool<Postgres> {
    info!("Connecting to database: {}", db_url);

    // Creazione del pool
    let pool: Pool<Postgres> = PoolOptions::<Postgres>::new()
            .max_connections(5)
            .connect(db_url)
            .await.expect("Failed to connect to DB");

    // Verifica della connessione
    let check: i32 = sqlx::query("SELECT 1")
            .fetch_one(&pool)
            .await
            .expect("Error checking for connection")
            .get(0);
    debug!("Test della connessione: {}", check);

    // Non serve il to_owned, il pool viene restituito per valore (Pool è già un handle condivisibile/clonabile)
    pool
}
