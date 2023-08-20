package com.example.skillsinema.ui.main.find

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.RepositoryKeyWord
import com.example.skillsinema.ui.main.home.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(FlowPreview::class)
class SearchViewmodel @Inject constructor(
    private val keyWord: RepositoryKeyWord
) : ViewModel() {


    private var _keyWordsFilm = MutableStateFlow<List<Film>>(emptyList())
    val keyWordsFilms = _keyWordsFilm.asStateFlow()

    //val keyfilms by mutableStateOf(keyWordsFilms)
    private var _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()


    var _isSearching = MutableStateFlow("")
    var isSearching = _isSearching.asStateFlow()


    fun onSearchTextChange(text: String) {

        _searchText.value = text
        keyWordsFilms()
    }

    init {

        viewModelScope.launch {
            keyWordsFilms()
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

                keyWord.getKeyWord(searchText.value)


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


    val getPersons = searchText
        .debounce(1000)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _keyWordsFilm.value

        )


}