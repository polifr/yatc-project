use core::fmt;

use serde::{Deserialize, Serialize};

#[derive(Debug, Deserialize, Serialize)]
pub struct TestEvent {
    message: String,

    #[serde(rename = "creationTime")]
    creation_time: String,
}

impl fmt::Display for TestEvent {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "[TestEvent: message={}, creation_time={}]", self.message, self.creation_time)
    }
}
