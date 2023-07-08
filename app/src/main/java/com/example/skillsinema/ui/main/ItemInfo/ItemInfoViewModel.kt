package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.skillsinema.DataRepository
import com.example.skillsinema.adapter.Film
import com.example.skillsinema.datasource.StaffDataSource
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.domain.GetFilmDetailUseCase
import com.example.skillsinema.entity.ModelStaffItem
//import com.example.skillsinema.entity.ModelFilmDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private var dataRepository: DataRepository,
    private val dataFilm: GetFilmDetailUseCase,
    private val pagingSource: StaffDataSource) :
    ViewModel() {
    private val _film = MutableLiveData<ModelFilmDetails>()
    //private val _film= MutableStateFlow<ModelFilmDetails.Film>()
    //val film = _film as StateFlow<*>
    val film = _film

    fun getValue(): Int {
        return dataRepository.id
    }

    fun setValue(value: Int) {
        dataRepository.id = value
    }


    //Log.d("ItemInfoViewModel", "${id}" )
    init {
        viewModelScope.launch {
            loadFilm()

        }
        getValue()


    }


    fun loadFilm() {

        // film.value?.kinopoiskId=id


        viewModelScope.launch {
            // repository.getFilmDetails(id).film
            kotlin.runCatching {

                dataFilm.executeGetFilm(getValue())
              //  Log.d("ItemInfoViewModel", "${id}" )
            }.fold(
                onSuccess = {
                    _film.value = it
                    Log.d("ItemInfoViewModel", "${it}" )
                },
                onFailure = {
                    Log.d("1ItemInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }



    val pagedStaff: Flow<PagingData<ModelStaffItem>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)


}