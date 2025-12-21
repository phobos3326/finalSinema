package com.example.skillsinema.domain.datasource

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.domain.repository.FilmRepository
import com.example.skillsinema.domain.usecase.GetRandomFiltersUseCase
import kotlinx.coroutines.flow.*
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsDataSource @Inject constructor(
    private val filmRepository: FilmRepository,
    private val getRandomFilters: GetRandomFiltersUseCase
) {
    private val _filteredFilmsFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val filteredFilmsFlow: SharedFlow<List<Film>> = _filteredFilmsFlow.asSharedFlow()

    // ✅ Кэшируем параметры фильтров
    private var cachedFilterParams: FilterParams? = null

    suspend fun loadFilteredFilms() {
        // ✅ Генерируем фильтры только один раз
        if (cachedFilterParams == null) {
            cachedFilterParams = getRandomFilters()
        }

        val films = filmRepository.getFilteredFilms(cachedFilterParams!!)
        _filteredFilmsFlow.emit(films)
    }

    // Получить текущие параметры (для отображения в UI)
    fun getCurrentFilterParams(): FilterParams? = cachedFilterParams

    // Сброс кэша (для генерации новых фильтров)
    fun clearFilterCache() {
        cachedFilterParams = null
    }
}
