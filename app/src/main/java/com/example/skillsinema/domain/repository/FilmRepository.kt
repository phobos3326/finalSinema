package com.example.skillsinema.domain.repository

import com.example.skillsinema.data.model.ModelFilter
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilmDetails
import com.example.skillsinema.domain.model.FilterParams
import retrofit2.Response


interface FilmRepository {
    suspend fun getPremieres(year: Int, month: String): List<Film>
    suspend fun getFilmDetails(id: Int): FilmDetails
    suspend fun getTopFilms(): List<Film>
    suspend fun getFilteredFilms(filterParams: FilterParams): List<Film>
    suspend fun getSimilarFilms(filmId: Int): List<Film>
    suspend fun getSerials(): List<Film>
    suspend fun getFilters(): Response<ModelFilter>
}