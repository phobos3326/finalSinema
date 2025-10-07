package com.example.skillsinema.domain.model

data class AppState(
    val selectedFilmId: Int = 0,
    val selectedActorId: Int = 0,
    val imageType: String = "",
    val filterParams: FilterParams = FilterParams(),
    val searchQuery: String = "",
    val selectedSeriesId: Int? = null
)