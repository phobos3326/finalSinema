package com.example.skillsinema.domain.model

data class Film(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val genres: List<Genre> = emptyList(),
    val countries: List<Country> = emptyList(),
    val rating: Double?
)