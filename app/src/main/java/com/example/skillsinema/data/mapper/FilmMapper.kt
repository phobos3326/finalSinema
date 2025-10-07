package com.example.skillsinema.data.mapper


import com.example.skillsinema.data.dto.*
import com.example.skillsinema.domain.model.*
import javax.inject.Inject

class FilmMapper @Inject constructor() {

    fun mapFilm(dto: FilmDto): Film {
        return Film(
            kinopoiskId = dto.filmId ?: dto.kinopoiskId,
            nameRu = dto.nameRu,
            nameEn = dto.nameEn,
            year = dto.year,
            posterUrl = dto.posterUrl,
            posterUrlPreview = dto.posterUrlPreview,
            genres = dto.genres.map { mapGenre(it) },
            countries = dto.countries.map { mapCountry(it) },
            rating = dto.rating
        )
    }

    fun mapFilmList(dtos: List<FilmDto>): List<Film> {
        return dtos.map { mapFilm(it) }
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
            genres = dto.genres.map { mapGenre(it) },
            countries = dto.countries.map { mapCountry(it) },
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

    private fun mapGenre(dto: GenreDto): Genre {
        return Genre(
            id = dto.id,
            genre = dto.genre
        )
    }

    private fun mapCountry(dto: CountryDto): Country {
        return Country(
            id = dto.id,
            country = dto.country
        )
    }
}
