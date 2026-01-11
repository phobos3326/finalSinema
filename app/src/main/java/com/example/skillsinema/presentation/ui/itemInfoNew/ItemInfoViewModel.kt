package com.example.skillsinema.presentation.ui.itemInfoNew

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.dao.LikedFilmRepository
import com.example.skillsinema.dao.LikedFilms
import com.example.skillsinema.datasource.GalerieDataSource
import com.example.skillsinema.domain.GetFilmDetailUseCase
import com.example.skillsinema.domain.GetStaffUseCase
import com.example.skillsinema.domain.LoadItemToDB
import com.example.skillsinema.domain.SimilarFilmsUsecase
import com.example.skillsinema.domain.collections.usecase.CreateCollectionUseCase
import com.example.skillsinema.domain.collections.usecase.GetCollectionsUiUseCase
import com.example.skillsinema.domain.collections.usecase.ToggleFilmInCollectionUseCase
import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.entity.ModelStaff
import com.example.skillsinema.presentation.base.BaseViewModel
import com.example.skillsinema.ui.main.ItemInfo.StateItemFilmInfo
import com.example.skillsinema.ui.main.home.TypeItem
import com.example.skillsinema.ui.main.profile.menu.CollectionsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private val dataFilm: GetFilmDetailUseCase,
    private val staffUseCase: GetStaffUseCase,
    private val galerieDataSource: GalerieDataSource,
    private val dataRepository: DataRepository,
    private val similarFilmsUsecase: SimilarFilmsUsecase,
    private val likedFilmRepository: LikedFilmRepository,
    private val loadItemToDB: LoadItemToDB,
    // Collections (SOLID)
    private val getCollectionsUi: GetCollectionsUiUseCase,
    private val createCollection: CreateCollectionUseCase,
    private val toggleFilmInCollection: ToggleFilmInCollectionUseCase,
) : BaseViewModel() {




    private var started = false

    fun start(filmId: Int) {
        if (started) return
        if (filmId <= 0) return
        started = true

        setValue(filmId)

        viewModelScope.launch {
            loadStaff()
            loadSimilarFilm()
            _state.value = StateItemFilmInfo.FilmState
            loadFilm()
            refreshCollections()
        }
    }

    // ===== Collections (UI state) =====
    private val _collectionUi = MutableStateFlow<List<CollectionsUiModel>>(emptyList())
    val collectionUi = _collectionUi.asStateFlow()

    fun insertIdtoDB(nameCollection: String) {
        viewModelScope.launch {
            createCollection(nameCollection)
            refreshCollections()
        }
    }

    fun update(uiModel: CollectionsUiModel) {
        viewModelScope.launch {
            toggleFilmInCollection(uiModel.collectionName, getValue())
            refreshCollections()
        }
    }

    private suspend fun refreshCollections() {
        _collectionUi.value = getCollectionsUi(getValue())
    }

    // ===== Screen state =====
    private val _state = MutableStateFlow<StateItemFilmInfo>(StateItemFilmInfo.FilmState)
    val state = _state.asStateFlow()

    private val _isLikedState = MutableStateFlow(false)
    val isLikedState = _isLikedState.asStateFlow()

    private val _film = MutableLiveData<ModelFilmDetails>()
    val film = _film

    private val _staff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val staff = _staff.asStateFlow()

    private val _noActorStaff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val noActorStaff = _noActorStaff.asStateFlow()

    private val _similar = MutableStateFlow<List<Film>>(emptyList())
    val similar = _similar.asStateFlow()

    private val _id = MutableStateFlow(0)
    val id = _id.asStateFlow()

    var actorList = mutableListOf<ModelStaff.ModelStaffItem>()
    var noActorList = mutableListOf<ModelStaff.ModelStaffItem>()

    fun getValue(): Int {
        _id.value = dataRepository.id
        return dataRepository.id
    }

    fun setValue(value: Int) {
        dataRepository.id = value
        _id.value = value
    }

    fun setSeriesValue(value: Int) {
        dataRepository.seriesID = value
    }



    fun isertItemToDb(type: TypeItem, id: Int) {
        viewModelScope.launch {
            loadItemToDB.getItemToDB(type, id)
        }
    }

    fun insertItemIsLiked(id: Int) {
        viewModelScope.launch {
            if (_isLikedState.value == false) {
                _isLikedState.value = true
                likedFilmRepository.insertLikedFilm(LikedFilms(id = id))
            } else {
                _isLikedState.value = false
                likedFilmRepository.delete(LikedFilms(id = id))
            }
        }
    }

    suspend fun loadFilm() {
        viewModelScope.launch {
            kotlin.runCatching {
                dataFilm.executeGetFilm(getValue())
            }.fold(
                onSuccess = { details ->
                    _film.value = details

                    _state.value = if (details.serial == false) {
                        StateItemFilmInfo.FilmState
                    } else {
                        StateItemFilmInfo.SerialState
                    }

                    withContext(Dispatchers.IO) {
                        val db = likedFilmRepository.getAll()
                        _isLikedState.value = db.any { it.id == details.kinopoiskId }
                    }
                },
                onFailure = {
                    Log.d("ItemInfoViewModel", it.message ?: "not load")
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
                },
                onFailure = {
                    Log.d("ItemInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }

    private fun loadStaff() {
        viewModelScope.launch {
            kotlin.runCatching {
                staffUseCase.getStaff()
            }.fold(
                onSuccess = { list ->
                    actorList = mutableListOf()
                    noActorList = mutableListOf()

                    list?.forEach { item ->
                        if (item.professionKey == "ACTOR") actorList.add(item)
                        else noActorList.add(item)
                    }

                    _staff.value = actorList
                    _noActorStaff.value = noActorList
                },
                onFailure = {
                    Log.d("ItemInfoViewModel", it.message ?: "not load")
                }
            )
        }
    }

    val pagedGalerie: Flow<PagingData<ModelGalerie.Item>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { galerieDataSource }
        ).flow.cachedIn(viewModelScope)
}
