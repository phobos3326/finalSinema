package com.example.skillsinema.domain.usecase

import com.example.skillsinema.domain.datasource.FilmsDataSource
import com.example.skillsinema.domain.model.Film
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmsByCategoryUseCase @Inject constructor(
    private val filmsDataSource: FilmsDataSource
) {
    // ✅ Возвращаем Flow для нужной категории
    operator fun invoke(category: String): Flow<List<Film>> {
        return when (category) {
            "premieres" -> filmsDataSource.premieresFlow
            "top_films" -> filmsDataSource.topFilmsFlow
            "serials" -> filmsDataSource.serialsFlow
            "filtered" -> filmsDataSource.filteredFilmsFlow
            else -> throw IllegalArgumentException("Unknown category: $category")
        }
    }

    // ✅ Загрузка данных для категории
    suspend fun loadCategory(category: String) {
        when (category) {
            "premieres" -> filmsDataSource.loadPremieres()
            "top_films" -> filmsDataSource.loadTopFilms()
            "serials" -> filmsDataSource.loadSerials()
            "filtered" -> filmsDataSource.loadFilteredFilms()
        }
    }

    // ✅ Загрузка всех категорий
    suspend fun loadAll() {
        filmsDataSource.loadAll()
    }
}
