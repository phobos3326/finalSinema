package com.example.skillsinema.ui.main.find

data class SearchUiState(
    val countries: Int = 0,
    var genre: Int = 0,
    var order: String ="",
    var filmType: String ="",
    var ratingFrom: Int = 0,
    var ratingTo: Int = 0,
    var yearFrom: Int = 0,
    var yearTo: Int = 0,
    var imdbId: String ="",
    var keyword: String ="",
    var page: Int = 0,
)
