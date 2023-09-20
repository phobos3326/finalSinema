package com.example.skillsinema.ui.main.find

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.dao.ItemFilm
import com.example.skillsinema.dao.ItemRepository
import com.example.skillsinema.datasource.SearchPagingSource
import com.example.skillsinema.domain.FiltersUseCase

import com.example.skillsinema.domain.searchFilmUseCase

import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelFilter
import com.example.skillsinema.ui.main.home.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date
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
            showYear
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

    val year = SimpleDateFormat("yyyy")
    val currentYear = year.format(Date())

    private val yearList = (1900..currentYear.toInt()).map { it }
    var previewList = mutableListOf<Int>()
    private var _showYear = MutableStateFlow(yearList)
    var showYear = _showYear.asStateFlow()

    private val _buttonIncrementEnabledState = MutableStateFlow(true)
    val buttonIncrementEnabledState: StateFlow<Boolean> = _buttonIncrementEnabledState

    private val _buttonDecrementEnabledState = MutableStateFlow(false)
    val buttonDecrementEnabledState: StateFlow<Boolean> = _buttonDecrementEnabledState

    var startIndex = 0
    var endIndex=startIndex+12



    fun selectedYearsIncrement(){
        if (endIndex > yearList.size) {
            endIndex = yearList.size
            _buttonIncrementEnabledState.value=false
        }
        _buttonDecrementEnabledState.value=true
      previewList=yearList.subList(startIndex, endIndex).toMutableList()
        _showYear.value=previewList
        startIndex = endIndex
        endIndex += 12

    }
    fun selectedYearsDecrement() {
        endIndex = startIndex
        startIndex -= 12
        if (startIndex <= 0) {
            startIndex = 0
            _buttonDecrementEnabledState.value = false
            _buttonIncrementEnabledState.value=true
        }
        previewList = yearList.subList(startIndex, endIndex).toMutableList()
        _showYear.value = previewList
        _buttonIncrementEnabledState.value=true
    }




}