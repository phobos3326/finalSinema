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
        ModelFilter.Country("США", 1),
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
                    _searchCountry.value = it as MutableList<ModelFilter.Country>
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
                    _searchGenre.value = it as MutableList<ModelFilter.Genre>

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

    fun setCountryQuery(country: Int, countryName: String) {
        dataRepository.countries = country
        dataRepository.countriesName = countryName

    }

    fun setGenreQuery(genre: Int, genreName: String) {
        dataRepository.genre = genre
        dataRepository.genreName = genreName

    }

    fun setFilmType(type: String) {
        dataRepository.filmType = type

    }

    fun setOrder(order: String) {
        dataRepository.order = order

    }

    fun setPeriodFrom(year: Int) {
        dataRepository.yearFrom = year

    }

    fun setPeriodTo(year: Int) {
        dataRepository.yearTo = year

    }


    fun getPeriod(): String {
        return "c ${dataRepository.yearFrom} до ${dataRepository.yearTo}"
    }

    fun getGenre() = "${dataRepository.genreName}"
    fun setRating(start: Int, end: Int) {
        dataRepository.ratingFrom = start
        dataRepository.ratingTo = end
    }

    val year = SimpleDateFormat("yyyy")
    val currentYear = year.format(Date())


    private val _currentPage = MutableStateFlow(5)
    val currentPage = _currentPage.asStateFlow()


    private val _currentPageTo = MutableStateFlow(5)
    val currentPageTo = _currentPageTo.asStateFlow()

    private val myList = (1900..currentYear.toInt()).map { it }


    val itemsPerPage = 12

    fun getCurrentPageItemsFrom(): List<Int> {
        val startIndex = currentPage.value * itemsPerPage
        val endIndex = minOf(startIndex + itemsPerPage, myList.size)
        return myList.subList(startIndex, endIndex)
    }

    fun getCurrentPageItemsTo(): List<Int> {
        val startIndex = currentPageTo.value * itemsPerPage
        val endIndex = minOf(startIndex + itemsPerPage, myList.size)
        return myList.subList(startIndex, endIndex)
    }

    fun incrementPageFrom() {
        _currentPage.value++
    }

    fun decrementPageFrom() {
        if (currentPage.value > 0) {
            _currentPage.value--
        }
    }


    fun incrementPageTo() {
        _currentPageTo.value++
    }

    fun decrementPageTo() {
        if (currentPageTo.value > 0) {
            _currentPageTo.value--
        }
    }

}