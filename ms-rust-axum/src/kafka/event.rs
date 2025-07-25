use serde::{Deserialize, Serialize};

#[derive(Debug, Deserialize, Serialize)]
pub struct TestEvent {
    message: String,
    creation_time: String,
}
