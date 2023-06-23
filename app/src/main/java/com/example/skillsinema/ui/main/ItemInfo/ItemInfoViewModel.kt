package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.adapter.ModelFilmDetails
import com.example.skillsinema.domain.GetFilmDetailUseCase
//import com.example.skillsinema.entity.ModelFilmDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemInfoViewModel @Inject constructor(private val dataFilm: GetFilmDetailUseCase) :
    ViewModel() {
    private val _film = MutableLiveData<ModelFilmDetails.Film>()
    //private val _film= MutableStateFlow<ModelFilmDetails.Film>()
    //val film = _film as StateFlow<*>
    val film11 = _film


    var id: Int = 0
    //Log.d("ItemInfoViewModel", "${id}" )
    init {
        viewModelScope.launch {
            loadFilm()

        }
    }


    fun loadFilm() {

        // film.value?.kinopoiskId=id


        viewModelScope.launch(Dispatchers.Default) {
            // repository.getFilmDetails(id).film
            kotlin.runCatching {
                dataFilm.executeGetFilm(id)
              //  Log.d("ItemInfoViewModel", "${id}" )
            }.fold(
                onSuccess = {
                    _film.value = it.film
                    Log.d("ItemInfoViewModel", "${it.film}" )
                },
                onFailure = {
                    Log.d("1ItemInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }

}