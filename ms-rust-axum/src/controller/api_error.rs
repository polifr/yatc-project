use axum::response::IntoResponse;

use crate::service::service_error::ServiceError;

pub enum ApiError {
    InternalServerError,
    NotFound,
}

impl From<ServiceError> for ApiError {
    fn from(error: ServiceError) -> Self {
        match error {
            ServiceError::Database(_) => {
                ApiError::InternalServerError
            }
        }
    }
}

impl IntoResponse for ApiError {
    fn into_response(self) -> axum::response::Response {
        let status = match self {
            ApiError::InternalServerError =>
                axum::http::StatusCode::INTERNAL_SERVER_ERROR,
            ApiError::NotFound =>
                axum::http::StatusCode::NOT_FOUND,
        };

        status.into_response()
    }
}