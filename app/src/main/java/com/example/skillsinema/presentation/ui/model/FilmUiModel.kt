package com.example.skillsinema.presentation.ui.model

/**
 * UI модель фильма
 */
data class FilmUiModel(
    val id: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: String?,
    val rating: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val genres: List<String>,
    val countries: List<String>
)
