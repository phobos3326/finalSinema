package com.example.skillsinema.ui.main.find

import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.dao.ItemFilm
import com.example.skillsinema.dao.ItemRepository
import com.example.skillsinema.datasource.FilteredFilmPagingSource
import com.example.skillsinema.datasource.SearchPagingSource
import com.example.skillsinema.domain.FiltersUseCase

import com.example.skillsinema.domain.searchFilmUseCase

import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelFilter
import com.example.skillsinema.repository.RepositoryKeyWord
import com.example.skillsinema.ui.main.home.MainViewModel
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class SearchViewmodel @Inject constructor(
    private var dataRepository: DataRepository,
    private val searchFilmUseCase: searchFilmUseCase,
    private val itemRepository: ItemRepository,
    private val useCase: FiltersUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow(DataRepository())
    val uiState: StateFlow<DataRepository> = _uiState.asStateFlow()


    val defCountry = mutableListOf(
        ModelFilter.Country("Россия", 34),
        ModelFilter.Country("Великобритания", 5),
        ModelFilter.Country("Германия", 9),
        ModelFilter.Country("Франция", 3),
    )


    val defGenre = mutableListOf(
        ModelFilter.Genre("Комедия", 13),
        ModelFilter.Genre("Мелодрама", 4),
        ModelFilter.Genre("Боевик", 11),
        ModelFilter.Genre("Вестерн", 10),
        ModelFilter.Genre("Драма", 2),
    )

    val Query = ""

    private var _searchCountry = MutableStateFlow(defCountry)
    var searchCountry = _searchCountry.asStateFlow()


    private var _searchGenre = MutableStateFlow(defGenre)
    var searchGenre = _searchGenre.asStateFlow()

    fun getFilteredCountriesFlow(): Flow<List<ModelFilter.Country>> {
        return _searchCountry.map { countries ->
            countries.filter {
                it.country.contains(Query, ignoreCase = false)
            }
        }
    }


    fun loadCountries() {


        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {

                useCase.getFilters().body()?.countries

            }.fold(
                onSuccess = {
                    // _topFilmModel.value = it
                    _searchCountry.value = it as MutableList<ModelFilter.Country>

                    // Log.d(TAG, "LIST FILM" + isViewed(it))
                },
                onFailure = { Log.d(MainViewModel.TAG, it.message ?: "not load") }
            )
        }


    }


    fun loadGenre() {


        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {

                useCase.getFilters().body()?.genres

            }.fold(
                onSuccess = {
                    // _topFilmModel.value = it
                    _searchGenre.value = it as MutableList<ModelFilter.Genre>

                    // Log.d(TAG, "LIST FILM" + isViewed(it))
                },
                onFailure = { Log.d(MainViewModel.TAG, it.message ?: "not load") }
            )
        }


    }


    init {

        viewModelScope.launch {
            searchResults
        }
    }

    var list = emptyList<Film>()


    fun insertItem(id: Int) {
        viewModelScope.launch {
            itemRepository.insertItem((ItemFilm(id = id)))
        }
    }


    private val _searchQuery = MutableStateFlow("")
    var searchQuery = _searchQuery.asStateFlow()


    val searchResults: Flow<PagingData<Film>> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { SearchPagingSource(searchFilmUseCase, dataRepository) }
            ).flow.cachedIn(viewModelScope)
        }

    fun setSearchQuery(query: String) {
        dataRepository.keyword = query
        _searchQuery.value = query
    }

    fun setCountryQuery(country: Int) {
        dataRepository.countries = country

    }

    fun setGenreQuery(genre: Int) {
        dataRepository.genre = genre

    }

    fun setFilmType(type: String) {
        dataRepository.filmType = type

    }

    fun setOrder(order: String) {
        dataRepository.order = order

    }

    fun setRating(start: Int, end: Int) {
        dataRepository.ratingFrom = start
        dataRepository.ratingTo = end
    }


}