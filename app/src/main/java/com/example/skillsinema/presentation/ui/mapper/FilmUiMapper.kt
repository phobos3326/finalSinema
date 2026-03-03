package com.example.skillsinema.presentation.ui.mapper

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.presentation.ui.model.FilmUiModel

/**
 * Маппер для преобразования domain моделей в UI модели
 */
object FilmUiMapper {

    fun toUiModel(film: Film): FilmUiModel {
        return FilmUiModel(
            id = film.kinopoiskId,
            nameRu = film.nameRu,
            nameEn = film.nameEn,
            year = film.year,
            rating = film.rating?.toString(),
            posterUrl = film.posterUrl,
            posterUrlPreview = film.posterUrlPreview,
            genres = film.genres.mapNotNull { it.genre },
            countries = film.countries.mapNotNull { it.country }
        )
    }

    fun toUiModelList(films: List<Film>): List<FilmUiModel> {
        return films.map { toUiModel(it) }
    }
}
