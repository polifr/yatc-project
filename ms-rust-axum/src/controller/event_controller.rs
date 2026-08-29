use axum::{
    Json, 
    Router, 
    extract::State, 
    routing::get,
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

pub fn routes() -> Router<AppState> {
    Router::new().route("/v1", get(find_all))
}
