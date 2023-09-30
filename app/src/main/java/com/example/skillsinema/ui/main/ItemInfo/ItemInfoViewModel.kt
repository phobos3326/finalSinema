package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.skillsinema.DataRepository

import com.example.skillsinema.datasource.GalerieDataSource

import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.domain.GetFilmDetailUseCase
import com.example.skillsinema.domain.GetStaffUseCase
import com.example.skillsinema.domain.SimilarFilmsUsecase
import com.example.skillsinema.entity.Film


import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.entity.ModelStaff


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private val dataFilm: GetFilmDetailUseCase,
    private val useCase: GetStaffUseCase,
    private val galerieDataSource: GalerieDataSource,
    //private val similarFilm: RepositorySimilarFilm,
    private val dataRepository: DataRepository,
    private val similarFilmsUsecase: SimilarFilmsUsecase
) :
    ViewModel() {


    private val _state = MutableStateFlow<StateItemFilmInfo>(StateItemFilmInfo.FilmState)
    val state = _state.asStateFlow()

    private val _film = MutableLiveData<ModelFilmDetails>()
    val film = _film


    private var _staff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val staff = _staff.asStateFlow()

    var actorList = mutableListOf<ModelStaff.ModelStaffItem>()

    private var _noActorStaff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val noActorStaff = _noActorStaff.asStateFlow()


    private var _similar = MutableStateFlow<List<Film>>(emptyList())
    val similar = _similar.asStateFlow()

    var noActorList = mutableListOf<ModelStaff.ModelStaffItem>()

    fun getValue(): Int {
        return dataRepository.id
    }

    fun setValue(value: Int) {
        dataRepository.id = value
    }


    init {
        viewModelScope.launch {
            loadFilm()
            loadStaff()
            pagedGalerie

            loadSimilarFilm()
            _state.value=StateItemFilmInfo.FilmState
        }

    }


    fun loadFilm() {
        viewModelScope.launch {
            kotlin.runCatching {
                dataFilm.executeGetFilm(getValue())
            }.fold(
                onSuccess = {
                    _film.value = it
                    if(it.serial==false){
                        _state.value=StateItemFilmInfo.FilmState
                    }else{
                        _state.value=StateItemFilmInfo.SerialState
                    }
                    Log.d("ItemInfoViewModel", "${it}")
                },
                onFailure = {
                    Log.d("1ItemInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }


    fun loadSimilarFilm() {
        viewModelScope.launch {
            kotlin.runCatching {
                similarFilmsUsecase.getSimilarFilms()
            }.fold(
                onSuccess = {
                    _similar.value = it
                    Log.d("ItemInfoViewModel", "${it}")
                },
                onFailure = {
                    Log.d("1ItemInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }


    private fun loadStaff() {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.getStaff()
            }.fold(
                onSuccess = {
                    it?.forEachIndexed { index, modelStaffItem ->
                        if (modelStaffItem.professionKey == "ACTOR") {
                            actorList.add(it[index])
                        } else {
                            noActorList.add(it[index])
                        }
                    }
                    _staff.value = actorList
                    _noActorStaff.value = noActorList
                    Log.d("MainViewModel2", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModelloadTopFilms", it.message ?: "not load") }
            )
        }
    }


    val pagedGalerie: Flow<PagingData<ModelGalerie.Item>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { galerieDataSource }
    ).flow.cachedIn(viewModelScope)


}