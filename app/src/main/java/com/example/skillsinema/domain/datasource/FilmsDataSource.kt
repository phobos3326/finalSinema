package com.example.skillsinema.domain.datasource

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.domain.repository.FilmRepository

import com.example.skillsinema.domain.usecase.filter.GetRandomFiltersUseCase
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
    // ✅ SharedFlow для каждой категории - кэшируется на уровне приложения
    private val _premieresFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val premieresFlow: SharedFlow<List<Film>> = _premieresFlow.asSharedFlow()

    private val _topFilmsFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val topFilmsFlow: SharedFlow<List<Film>> = _topFilmsFlow.asSharedFlow()

    private val _serialsFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val serialsFlow: SharedFlow<List<Film>> = _serialsFlow.asSharedFlow()

    private val _filteredFilmsFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val filteredFilmsFlow: SharedFlow<List<Film>> = _filteredFilmsFlow.asSharedFlow()

    // Кэш параметров
    private var cachedFilterParams: FilterParams? = null
    private var cachedYear: Int? = null
    private var cachedMonth: String? = null

    // ✅ Загружаем премьеры
    suspend fun loadPremieres() {
        if (cachedYear == null || cachedMonth == null) {
            val calendar = Calendar.getInstance()
            val monthNumber = calendar.get(Calendar.MONTH)
            cachedMonth = DateFormatSymbols(Locale.ENGLISH).months[monthNumber]
            cachedYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toInt()
        }

        val films = filmRepository.getPremieres(cachedYear!!, cachedMonth!!)
        _premieresFlow.emit(films)
    }

    // ✅ Загружаем топ фильмы
    suspend fun loadTopFilms() {
        val films = filmRepository.getTopFilms()
        _topFilmsFlow.emit(films)
    }

    // ✅ Загружаем сериалы
    suspend fun loadSerials() {
        val films = filmRepository.getSerials()
        _serialsFlow.emit(films)
    }

    // ✅ Загружаем фильтрованные фильмы (генерируем фильтры один раз)
    suspend fun loadFilteredFilms() {
        if (cachedFilterParams == null) {
            cachedFilterParams = getRandomFilters()
        }

        val films = filmRepository.getFilteredFilms(cachedFilterParams!!)
        _filteredFilmsFlow.emit(films)
    }

    // ✅ Загружаем все данные
    suspend fun loadAll() {
        loadPremieres()
        loadTopFilms()
        loadSerials()
        loadFilteredFilms()
    }

    // Сброс кэша (для refresh)
    fun clearCache() {
        cachedFilterParams = null
        cachedYear = null
        cachedMonth = null
    }
}
