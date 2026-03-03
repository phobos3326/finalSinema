package com.example.skillsinema.presentation.ui.details

/**
 * UI модель деталей фильма
 */
data class FilmDetailsUiModel(
    val id: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: String?,
    val rating: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val genres: List<String>,
    val countries: List<String>,
    val filmLength: String?,
    val description: String?,
    val shortDescription: String?,
    val serial: Boolean,
    val startYear: Int?,
    val endYear: Int?
)
