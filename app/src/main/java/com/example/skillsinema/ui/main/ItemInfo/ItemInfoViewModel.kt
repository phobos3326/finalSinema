package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.skillsinema.DataRepository
import com.example.skillsinema.adapter.Film

import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.domain.GetFilmDetailUseCase
import com.example.skillsinema.domain.GetStaffUseCase
import com.example.skillsinema.entity.ModelStaff
import com.example.skillsinema.repository.RepositoryStaff


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private var dataRepository: DataRepository,
    private val dataFilm: GetFilmDetailUseCase,
    //private var pagingSource: StaffDataSource,
    private val useCase: GetStaffUseCase,
    val repositoryStaff: RepositoryStaff
) :
    ViewModel() {
    private val _film = MutableLiveData<ModelFilmDetails>()

    //private val _film= MutableStateFlow<ModelFilmDetails.Film>()
    //val film = _film as StateFlow<*>
    val film = _film


    private var _staff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val staff = _staff.asStateFlow()

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
loadStaff()

        repositoryStaff.provideRetrofit()
repositoryStaff.parseJSON()
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

        //  flowOf(PagingData.from(listOf(Movie)).toList() == listOf(model)
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {

                useCase.getStaff()
            }.fold(
                onSuccess = {
                    _staff.value = it!!

                    Log.d("MainViewModel2", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModelloadTopFilms", it.message ?: "not load") }
            )
        }


    }


}