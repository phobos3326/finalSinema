package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilmDetails
import com.example.skillsinema.domain.model.FilterParams



interface FilmRepository {
    suspend fun getPremieres(year: Int, month: String): List<Film>
    suspend fun getFilmDetails(id: Int): FilmDetails
    suspend fun getTopFilms(): List<Film>
    suspend fun getFilteredFilms(filterParams: FilterParams): List<Film>
    suspend fun getSimilarFilms(filmId: Int): List<Film>
}