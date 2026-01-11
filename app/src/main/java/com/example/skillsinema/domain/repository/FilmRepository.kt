package com.example.skillsinema.domain.repository

import androidx.paging.PagingData
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilterParams


import com.example.skillsinema.domain.model.*
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    suspend fun getFilters(): FiltersResponse
    suspend fun getFilteredFilms(filterParams: FilterParams): List<Film>
    suspend fun getPremieres(year: Int, month: String): List<Film>
    suspend fun getTopFilms(): List<Film>
    suspend fun getSerials(): List<Film>
    suspend fun getFilmById(filmId: Int): FilmDetails
    suspend fun searchFilms(query: String, page: Int): List<Film>
    suspend fun getSimilarFilms(filmId: Int): List<Film>


     fun getPremieresPaged(year: Int, month: String): Flow<PagingData<Film>>
     fun getTopFilmsPaged(): Flow<PagingData<Film>>
     fun getSerialsPaged(): Flow<PagingData<Film>>
     fun getFilteredFilmsPaged(params: FilterParams): Flow<PagingData<Film>>

}