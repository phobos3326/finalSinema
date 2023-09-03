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
import com.example.skillsinema.datasource.FilteredFilmPagingSource
import com.example.skillsinema.datasource.SearchPagingSource

import com.example.skillsinema.domain.searchFilmUseCase

import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.RepositoryKeyWord
import com.example.skillsinema.ui.main.home.MainViewModel
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
    private val filteredFilmPagingSource: FilteredFilmPagingSource,
    private val searchPagingSource : SearchPagingSource,
    private val searchFilmUseCase: searchFilmUseCase
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


    fun onSearchTextChange(text: String) {

       // _searchText.value = text
       dataRepository.keyword=text
        searchFilteredFilms
    }

    init {

        viewModelScope.launch {
           // keyWordsFilms()
            searchFilteredFilms
        }
    }

    var list = emptyList<Film>()

    suspend fun key() {
        viewModelScope.launch {

        }

        //Log.d(TAG, " keyWord  ${a}")
    }


    fun keyWordsFilms() {

        //  flowOf(PagingData.from(listOf(Movie)).toList() == listOf(model)
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
               // uiState.value.filmType

               /* keyWord.getKeyWord(
                    countries = dataRepository.countries,
                    genres= uiState.value.genre,
                    oreder=uiState.value.order,
                    type=uiState.value.filmType,
                    ratingFrom=uiState.value.ratingFrom,
                    ratingTo=uiState.value.ratingTo,
                    yearFrom=uiState.value.yearFrom,
                    yearTo=uiState.value.yearTo,
                    imdbId=uiState.value.imdbId,
                    keyword = searchText.value)*/
                searchFilmUseCase.getKeyWord(1)


            }.fold(
                onSuccess = {
                    // _topFilmModel.value = it

                    _keyWordsFilm.value = it


                    // Log.d(TAG, "LIST FILM" + isViewed(it))
                },
                onFailure = { Log.d(MainViewModel.TAG, it.message ?: "not load") }
            )
        }


    }

    val searchFilteredFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { searchPagingSource }
    ).flow.cachedIn(viewModelScope)


    val getPersons = searchText
        .debounce(1000)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _keyWordsFilm.value

        )


}