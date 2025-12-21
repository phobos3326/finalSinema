package com.example.skillsinema.domain.repository

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilterParams


import com.example.skillsinema.domain.model.*

interface FilmRepository {
    suspend fun getFilters(): FiltersResponse
    suspend fun getFilteredFilms(filterParams: FilterParams): List<Film>
    suspend fun getPremieres(year: Int, month: String): List<Film>
    suspend fun getTopFilms(): List<Film>
    suspend fun getSerials(): List<Film>
    suspend fun getFilmById(filmId: Int): FilmDetails
    suspend fun searchFilms(query: String, page: Int): List<Film>
    suspend fun getSimilarFilms(filmId: Int): List<Film>
}