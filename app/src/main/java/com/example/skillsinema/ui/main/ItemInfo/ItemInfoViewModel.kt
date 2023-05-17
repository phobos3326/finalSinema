package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.data.Repository
import com.example.skillsinema.domain.GetFilmDetailUseCase
import com.example.skillsinema.entity.ModelFilmDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemInfoViewModel @Inject constructor(private  val dataFilm:GetFilmDetailUseCase, val repository: Repository):ViewModel() {
    private val _film= MutableLiveData<ModelFilmDetails.Film>()
    val film =_film

    var id:Int =0

    init {
        viewModelScope.launch {
            loadFilm()

        }
    }

     fun loadFilm( ) {

       // film.value?.kinopoiskId=id



        viewModelScope.launch(Dispatchers.Default) {
           // repository.getFilmDetails(id).film
            kotlin.runCatching {
                dataFilm.executeGetFilm(id).film
            }.fold(
                onSuccess = {
                    _film.value=it
                },
                onFailure = { Log.d("ItemInfoViewModel", it.message ?: "not load" )}
            )
        }
    }

}