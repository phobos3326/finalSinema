package com.example.skillsinema.ui.main.find

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.RepositoryKeyWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class searchViewmodel @Inject constructor(
    private val keyWord: RepositoryKeyWord
):ViewModel() {

    init {

        viewModelScope.launch {
          key()
        }
    }

    var list = emptyList<Film>()

    suspend fun key() {
        list= keyWord.getKeyWord()!!

        //Log.d(TAG, " keyWord  ${a}")
    }

}