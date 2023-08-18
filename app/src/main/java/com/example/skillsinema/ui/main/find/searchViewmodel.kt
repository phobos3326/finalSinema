package com.example.skillsinema.ui.main.find

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.RepositoryKeyWord
import com.example.skillsinema.ui.main.home.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class searchViewmodel @Inject constructor(
    private val keyWord: RepositoryKeyWord
):ViewModel() {


    private var _keyWordsFilm = MutableStateFlow<List<Film>>(emptyList())
    val keyWordsFilms = _keyWordsFilm.asStateFlow()

    //val keyfilms by mutableStateOf(keyWordsFilms)

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


    private fun keyWordsFilms() {

        //  flowOf(PagingData.from(listOf(Movie)).toList() == listOf(model)
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {

                keyWord.getKeyWord()

            }.fold(
                onSuccess = {
                    // _topFilmModel.value = it
                    _keyWordsFilm.value=  it

                    // Log.d(TAG, "LIST FILM" + isViewed(it))
                },
                onFailure = { Log.d(MainViewModel.TAG, it.message ?: "not load") }
            )
        }


    }

}