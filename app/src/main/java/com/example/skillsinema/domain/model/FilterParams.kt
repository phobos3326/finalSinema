package com.example.skillsinema.domain.model

data class FilterParams(
    val countries: Int? = null,
    val genres: Int? = null,
    val ratingFrom: Int? = 0,
    val ratingTo: Int? = 10,
    val yearFrom: Int? = 1000,
    val yearTo: Int? = 3000,
    val order: String? = null,
    val filmType: String? = null,
    val keyword: String? = null,
    val page: Int = 1
)
