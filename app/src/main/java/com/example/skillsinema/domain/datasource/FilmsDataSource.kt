package com.example.skillsinema.domain.datasource

import android.util.Log
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.domain.repository.FilmRepository
import com.example.skillsinema.domain.usecase.GetRandomFiltersUseCase
import kotlinx.coroutines.flow.*
import java.text.DateFormatSymbols
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsDataSource @Inject constructor(
    private val filmRepository: FilmRepository,
    private val getRandomFilters: GetRandomFiltersUseCase
) {
    // Premieres Flow
    private val _premieresFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val premieresFlow: SharedFlow<List<Film>> = _premieresFlow.asSharedFlow()

    // Top Films Flow
    private val _topFilmsFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val topFilmsFlow: SharedFlow<List<Film>> = _topFilmsFlow.asSharedFlow()

    // Serials Flow
    private val _serialsFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val serialsFlow: SharedFlow<List<Film>> = _serialsFlow.asSharedFlow()

    // Filtered Films Flow
    private val _filteredFilmsFlow = MutableSharedFlow<List<Film>>(replay = 1)
    val filteredFilmsFlow: SharedFlow<List<Film>> = _filteredFilmsFlow.asSharedFlow()

    private var cachedFilterParams: FilterParams? = null

    suspend fun loadPremieres() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)

        val monthSymbols = DateFormatSymbols(Locale.ENGLISH)
        val month = monthSymbols.months[calendar.get(Calendar.MONTH)].uppercase()
        Log.d("PREMIERES", "year=$year month=$month")

        val films = filmRepository.getPremieres(year, month)
        _premieresFlow.emit(films)
    }

    suspend fun loadTopFilms() {
        val films = filmRepository.getTopFilms()
        _topFilmsFlow.emit(films)
    }

    suspend fun loadSerials() {
        val films = filmRepository.getSerials()
        _serialsFlow.emit(films)
    }

    suspend fun loadFilteredFilms() {
        if (cachedFilterParams == null) {
            cachedFilterParams = getRandomFilters()
        }
        val films = filmRepository.getFilteredFilms(cachedFilterParams!!)
        _filteredFilmsFlow.emit(films)
    }

    suspend fun loadAll() {
        loadPremieres()
        loadTopFilms()
        loadSerials()
        loadFilteredFilms()
    }

    fun getCurrentFilterParams(): FilterParams? = cachedFilterParams

    fun clearFilterCache() {
        cachedFilterParams = null
    }
}
