package com.example.skillsinema.domain.usecase.search

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.repository.SearchRepository
import javax.inject.Inject

class SearchFilmsUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): List<Film> {
        return searchRepository.searchFilms(query, page)
    }
}