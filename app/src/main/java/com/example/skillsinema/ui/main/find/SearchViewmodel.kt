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

import com.example.skillsinema.domain.searchFilmUseCase

import com.example.skillsinema.entity.Film
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
    private val keyWord: RepositoryKeyWord,
    // private val repository: RepositoryKeyWord,
    private val filteredFilmPagingSource: FilteredFilmPagingSource,
    private val searchPagingSource: SearchPagingSource,
    private val searchFilmUseCase: searchFilmUseCase,
    private val itemRepository: ItemRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(DataRepository())
    val uiState: StateFlow<DataRepository> = _uiState.asStateFlow()

    private var _keyWordsFilm = MutableStateFlow<List<Film>>(emptyList())
    val keyWordsFilms = _keyWordsFilm.asStateFlow()

    //val keyfilms by mutableStateOf(keyWordsFilms)
    private var _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()


    var _isSearching = MutableStateFlow("")
    var isSearching = _isSearching.asStateFlow()


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


    val searchFilteredFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { searchPagingSource }
    ).flow.cachedIn(viewModelScope)


}