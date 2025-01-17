package com.example.skillsinema.ui.main.actorInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.datasource.BestActorFilmPagingSource
import com.example.skillsinema.domain.GetActorFilmUseCase
import com.example.skillsinema.domain.GetActorUseCase
import com.example.skillsinema.domain.LoadItemToDB
import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelActorInfo
import com.example.skillsinema.entity.ModelStaff
import com.example.skillsinema.ui.main.home.TypeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorInfoViewModel @Inject constructor(
    private val useCase: GetActorUseCase,
    private val useCaseActorFilm: GetActorFilmUseCase,
    private val dataRepository: DataRepository,
    private val pagingSource: BestActorFilmPagingSource,
    private val loadItemToDB: LoadItemToDB
) : ViewModel() {
    private var _actor = MutableLiveData<ModelActorInfo>()
    val actor = _actor

    private var _actorFilm = MutableStateFlow<List<Film>>(emptyList())
    val actorFilm = _actorFilm.asStateFlow()


    fun setValue(value: Int) {
        dataRepository.idActor = value
    }

    init {

        //loadActorFilm()
        viewModelScope.launch {
            loadActor()
            pagedFilms
        }
    }


    fun isertItemToDb(type: TypeItem, id: Int) {
        viewModelScope.launch {
            loadItemToDB.getItemToDB(type, id)
        }

    }

    private fun loadActor() {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.getActor()
            }.fold(
                onSuccess = {
                    _actor.value = it
                    Log.d("ActorInfoViewModel", "${it}")
                },
                onFailure = {
                    Log.d("ActorInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }

    private fun loadActorFilm() {
        viewModelScope.launch {
            kotlin.runCatching {
                useCaseActorFilm.getActorFilm()
            }.fold(
                onSuccess = {
                    _actorFilm.value = it
                    Log.d("ActorInfoViewModel2", "${it}")
                },
                onFailure = {
                    Log.d("ActorInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }

    val pagedFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)

}