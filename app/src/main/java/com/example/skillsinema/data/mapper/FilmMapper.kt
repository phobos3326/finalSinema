package com.example.skillsinema.data.mapper


import com.example.skillsinema.data.dto.*
import com.example.skillsinema.domain.model.*
import javax.inject.Inject

class FilmMapper @Inject constructor() {

    fun mapFilmList(dtos: List<FilmDto>): List<Film> {
        return dtos.map { mapFilm(it) }
    }

    fun mapFilm(dto: FilmDto): Film {
        return Film(
            kinopoiskId = dto.filmId ?: dto.kinopoiskId ?: 0,  // ← ИСПРАВЛЕНО: добавить fallback
            nameRu = dto.nameRu,
            nameEn = dto.nameEn,
            year = dto.year,
            posterUrl = dto.posterUrl,
            posterUrlPreview = dto.posterUrlPreview,
            genres = dto.genres.mapNotNull { mapGenre(it) },
            countries = dto.countries.mapNotNull { mapCountry(it) },
            rating = dto.rating
        )
    }

    fun mapFilmDetails(dto: FilmDetailsDto): FilmDetails {
        return FilmDetails(
            kinopoiskId = dto.kinopoiskId,
            nameRu = dto.nameRu,
            nameEn = dto.nameEn,
            nameOriginal = dto.nameOriginal,
            year = dto.year,
            description = dto.description,
            shortDescription = dto.shortDescription,
            posterUrl = dto.posterUrl,
            posterUrlPreview = dto.posterUrlPreview,
            coverUrl = dto.coverUrl,
            logoUrl = dto.logoUrl,
            genres = dto.genres.mapNotNull { mapGenre(it) },      // ← mapNotNull
            countries = dto.countries.mapNotNull { mapCountry(it) }, // ← mapNotNull
            ratingKinopoisk = dto.ratingKinopoisk,
            ratingImdb = dto.ratingImdb,
            ratingFilmCritics = dto.ratingFilmCritics,
            ratingAwait = dto.ratingAwait,
            filmLength = dto.filmLength,
            slogan = dto.slogan,
            ageLimit = dto.ratingAgeLimits,
            startYear = dto.startYear,
            endYear = dto.endYear,
            serial = dto.serial,
            completed = dto.completed
        )
    }


    private fun mapGenre(dto: GenreDto): Genre? {
        return if (dto.id != null) {
            Genre(id = dto.id, genre = dto.genre)
        } else {
            null
        }
    }


    private fun mapCountry(dto: CountryDto): Country? {
        return if (dto.id != null) {
            Country(id = dto.id, country = dto.country)
        } else {
            null
        }
    }
}
