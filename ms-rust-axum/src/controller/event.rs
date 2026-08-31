use axum::{
    Json, Router, extract::{Path, State}, routing::get,
};

use crate::{
    controller::{
        api_error::ApiError, 
        app_state::AppState, 
        model::event_model::EventModel,
    }, 
};

pub async fn find_all(
    State(state): State<AppState>,
) -> Result<Json<Vec<EventModel>>, ApiError> {
    let events = state.event_service.find_all().await?;

    let models = events.into_iter().map(EventModel::from).collect();

    Ok(Json(models))
}

pub async fn find_by_id(
    State(state): State<AppState>, 
    Path(id): Path<i64>,
) -> Result<Json<EventModel>, ApiError> {
    let event = state.event_service.find_by_id(id).await?;

    match event {
        Some(event) => {
            Ok(Json(EventModel::from(event)))
        }
        None => {
            Err(ApiError::NotFound)
        }
    }
}

pub fn routes() -> Router<AppState> {
    Router::new()
        .route("/v1", get(find_all))
        .route("/v1/{id}", get(find_by_id))
}
