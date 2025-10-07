package com.example.skillsinema.data.repository

import com.example.skillsinema.data.api.SearchApi
import com.example.skillsinema.data.mapper.FilmMapper
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: SearchApi,
    private val mapper: FilmMapper
) : SearchRepository {

    override suspend fun searchFilms(query: String, page: Int): List<Film> {
        val response = api.searchFilms(query, page)
        return mapper.mapFilmList(response.items)
    }
}