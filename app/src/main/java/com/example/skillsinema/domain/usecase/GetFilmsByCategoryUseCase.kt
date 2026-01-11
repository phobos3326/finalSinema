package com.example.skillsinema.domain.usecase

import androidx.paging.PagingData
import com.example.skillsinema.domain.datasource.FilmsDataSource
import com.example.skillsinema.domain.model.Film
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmsByCategoryUseCase @Inject constructor(
    private val filmsDataSource: FilmsDataSource
) {
    operator fun invoke(category: String): Flow<List<Film>> {
        return when (category) {
            "premieres" -> filmsDataSource.premieresFlow
            "top_films" -> filmsDataSource.topFilmsFlow
            "serials" -> filmsDataSource.serialsFlow
            "filtered" -> filmsDataSource.filteredFilmsFlow
            else -> throw IllegalArgumentException("Unknown category: $category")
        }
    }

    // ✅ НОВОЕ: Paging-поток для ShowAll
    fun paged(category: String): Flow<PagingData<Film>> {
        return when (category) {
            "premieres" -> filmsDataSource.premieresPagingFlow()
            "top_films" -> filmsDataSource.topFilmsPagingFlow
            "serials" -> filmsDataSource.serialsPagingFlow
            "filtered" -> filmsDataSource.filteredFilmsPagingFlow()
            else -> throw IllegalArgumentException("Unknown category: $category")
        }
    }

    suspend fun loadCategory(category: String) {
        when (category) {
            "premieres" -> filmsDataSource.loadPremieres()
            "top_films" -> filmsDataSource.loadTopFilms()
            "serials" -> filmsDataSource.loadSerials()
            "filtered" -> filmsDataSource.loadFilteredFilms()
        }
    }

    suspend fun loadAll() = filmsDataSource.loadAll()
}
