package com.example.skillsinema.domain.model

data class FilmDetails(
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val year: Int?,
    val description: String?,
    val shortDescription: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val coverUrl: String?,
    val logoUrl: String?,
    val genres: List<Genre>,
    val countries: List<Country>,
    val ratingKinopoisk: Double?,
    val ratingImdb: Double?,
    val ratingFilmCritics: Double?,
    val ratingAwait: Double?,
    val filmLength: Int?,
    val slogan: String?,
    val ageLimit: String?,
    val startYear: Int?,
    val endYear: Int?,
    val serial: Boolean?,
    val completed: Boolean?
)