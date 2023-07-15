package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.skillsinema.DataRepository
import com.example.skillsinema.MyComponentManager
import com.example.skillsinema.MyEntryPoint
import com.example.skillsinema.datasource.GalerieDataSource

import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.domain.GetFilmDetailUseCase
import com.example.skillsinema.domain.GetStaffUseCase
import com.example.skillsinema.entity.Film


import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.entity.ModelStaff
import com.example.skillsinema.repository.RepositorySimilarFilm
import dagger.hilt.EntryPoints


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    //private var dataRepository: DataRepository,
    private val dataFilm: GetFilmDetailUseCase,
    private val useCase: GetStaffUseCase,
    private val galerieDataSource: GalerieDataSource,
    private val similarFilm: RepositorySimilarFilm,
    var myComponentManager: MyComponentManager

) :
    ViewModel() {
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



    val myComponent = myComponentManager.get()
    val rep =EntryPoints.get(myComponent, MyEntryPoint::class.java).getDataRepository()
    fun getValue(): Int {

        return rep.id
    }

    fun setValue(value: Int) {
        rep.id = value
    }



    //Log.d("ItemInfoViewModel", "${id}" )
    init {

        viewModelScope.launch {
            loadFilm()
            loadStaff()
//            getValue()

            pagedGalerie
          // dataRepository

            myComponentManager.create()
            //  repositoryStaff.provideRetrofit()
            loadSimilarFilm()
        }


//repositoryStaff.parseJSON()
    }


    /* val myComponent =myComponentManager.get()
     val dataRepository= EntryPoints.get(myComponent, MyEntryPoint::class.java).getDataRepository()
     */

    fun loadFilm() {

        // film.value?.kinopoiskId=id


        //rep.
        viewModelScope.launch {
            // repository.getFilmDetails(id).film
            kotlin.runCatching {


                dataFilm.executeGetFilm(getValue())
                //  Log.d("ItemInfoViewModel", "${id}" )
            }.fold(
                onSuccess = {
                    _film.value = it

                    Log.d("ItemInfoViewModel", "${it}")
                },
                onFailure = {
                    Log.d("1ItemInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }


    fun loadSimilarFilm() {

        // film.value?.kinopoiskId=id


        //rep.
        viewModelScope.launch {
            // repository.getFilmDetails(id).film
            kotlin.runCatching {


                similarFilm.getSimilarFilm()
                //  Log.d("ItemInfoViewModel", "${id}" )
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



    /*val pagedStaff: Flow<PagingData<ModelStaffItem>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)*/

    private fun loadStaff() {

        viewModelScope.launch {
            kotlin.runCatching {

                useCase.getStaff(getValue())
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