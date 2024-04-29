package com.example.skillsinema.ui.main.ItemInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.*
import com.example.skillsinema.DataRepository

import com.example.skillsinema.dao.CollectionEntityRepository
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.dao.LikedFilmRepository
import com.example.skillsinema.dao.LikedFilms

import com.example.skillsinema.datasource.GalerieDataSource

import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.domain.GetFilmDetailUseCase
import com.example.skillsinema.domain.GetStaffUseCase
import com.example.skillsinema.domain.LoadItemToDB
import com.example.skillsinema.domain.SimilarFilmsUsecase
import com.example.skillsinema.entity.Film


import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.entity.ModelStaff
import com.example.skillsinema.ui.main.home.TypeItem
import com.example.skillsinema.ui.main.profile.menu.CollectionsUiModel


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ItemInfoViewModel @Inject constructor(
    private val dataFilm: GetFilmDetailUseCase,
    private val useCase: GetStaffUseCase,
    private val galerieDataSource: GalerieDataSource,
    //private val similarFilm: RepositorySimilarFilm,
    private val dataRepository: DataRepository,
    private val similarFilmsUsecase: SimilarFilmsUsecase,
    private val likedFilmRepository: LikedFilmRepository,
    private val collectionEntityRepository: CollectionEntityRepository,
    private val loadItemToDB: LoadItemToDB

) :
    ViewModel() {


    private val _state = MutableStateFlow<StateItemFilmInfo>(StateItemFilmInfo.FilmState)
    val state = _state.asStateFlow()

    private val _isLikedState = MutableStateFlow<Boolean>(false)
    val isLikedState = _isLikedState.asStateFlow()

    private val _film = MutableLiveData<ModelFilmDetails>()
    val film = _film


    private var _staff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val staff = _staff.asStateFlow()

    var actorList = mutableListOf<ModelStaff.ModelStaffItem>()

    private var _noActorStaff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val noActorStaff = _noActorStaff.asStateFlow()


    private var _similar = MutableStateFlow<List<Film>>(emptyList())
    val similar = _similar.asStateFlow()


    private val _collection = MutableStateFlow<List<CollectionsEntity>>(emptyList())
    val collection = _collection.asStateFlow()


    private val _collectionUi = MutableStateFlow<List<CollectionsUiModel>>(emptyList())
    val collectionUi = _collectionUi.asStateFlow()

    private val _id = MutableStateFlow<Int>(0)
    val id = _id.asStateFlow()


    private val taskComplieted = Channel<Unit>()

    var noActorList = mutableListOf<ModelStaff.ModelStaffItem>()

    fun getValue(): Int {
        _id.value = dataRepository.id
        return dataRepository.id
    }

    fun setValue(value: Int) {
        dataRepository.id = value
    }

    fun setSeriesValue(value: Int) {
        dataRepository.seriesID = value
    }

    val sharedJob = Job()

    init {
        viewModelScope.launch {
            loadStaff()
            pagedGalerie
            loadSimilarFilm()
            _state.value = StateItemFilmInfo.FilmState
            loadFilm()
            //sharedJob.join()
            show()
            withContext(Dispatchers.IO){
               // loadItemToDB.getItemToDB(type = "film", getValue())
            }

        }


    }


    fun isertItemToDb(type: TypeItem, id: Int) {
        viewModelScope.launch {
            loadItemToDB.getItemToDB(type, id)
        }

    }

    fun insertItemIsLiked(id: Int) {
        viewModelScope.launch() {
            if (_isLikedState.value == false) {
                _isLikedState.value = true
                likedFilmRepository.insertLikedFilm((LikedFilms(id = id)))
            } else if (_isLikedState.value == true) {
                _isLikedState.value = false
                likedFilmRepository.delete((LikedFilms(id = id)))
            }
        }
    }

    fun insertCollection() {
        viewModelScope.launch {
            //val list= collectionEntityRepository.getListFilmFromCollection("name")

            collectionEntityRepository.insertCollection(
                CollectionsEntity(
                    0,
                    "name",
                    listOf(1, 2, 3, 4, 5)
                )
            )

        }
    }

    suspend fun loadFilm() {
        viewModelScope.launch() {
            kotlin.runCatching {
                dataFilm.executeGetFilm(getValue())
            }.fold(
                onSuccess = {
                    _film.value = it
                    if (it.serial == false) {
                        _state.value = StateItemFilmInfo.FilmState
                    } else {
                        _state.value = StateItemFilmInfo.SerialState
                    }
                    Log.d("ItemInfoViewModel", "${it}")
                    withContext(Dispatchers.IO) {
                        val db = likedFilmRepository.getAll()

                        for (element in db) {
                            if (film.value?.kinopoiskId == element.id) {
                                _isLikedState.value = true
                                break
                            } else {
                                _isLikedState.value = false
                            }
                        }
                    }
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


    fun insertIdtoDB(nameCollection: String) {
        viewModelScope.launch() {


            collectionEntityRepository.insertCollection(
                CollectionsEntity(
                    0,
                    nameCollection,
                    emptyList()
                )

            )

            withContext(Dispatchers.IO) {
                val db = collectionEntityRepository.getAll()

                _collection.value = db
                _collection.emit(db)

            }

        }

    }

    fun deleteFilmFromDB(colllection: CollectionsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            val dbList =
                collectionEntityRepository.getCollectionList(colllection.collectionName).collection

            val mutableDBList = dbList?.toMutableList()

            if (!mutableDBList?.contains(getValue())!!) {
                mutableDBList?.remove(getValue())
            }

            collectionEntityRepository.updateCollectionList(
                colllection.collectionName,
                mutableDBList
            )
            _collection.value = db
        }
    }


    fun update(uiModel: CollectionsUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            var updatedCollection = uiModel.collection?.toMutableList()
            var entity = mapUiModelToEntity(uiModel)
            if (uiModel.collection?.isEmpty() ?: true && !uiModel.isChecked) {
                updatedCollection = mutableListOf()
                updatedCollection?.add(getValue())
                val updatedModel = uiModel.copy(collection = updatedCollection, isChecked = true)
                entity = mapUiModelToEntity(updatedModel)
                collectionEntityRepository.updateCollectionList(
                    entity.collectionName,
                    entity.collection
                )

                show()
            } else if (uiModel.collection?.contains(getValue()) != true && !uiModel.isChecked) {
                updatedCollection?.add(getValue())
                val updatedModel = uiModel.copy(collection = updatedCollection, isChecked = true)
                entity = mapUiModelToEntity(updatedModel)
                collectionEntityRepository.updateCollectionList(
                    entity.collectionName,
                    entity.collection
                )

                show()
            } else {
                updatedCollection?.remove(getValue())
                val updatedModel = uiModel.copy(collection = updatedCollection, isChecked = false)
                entity = mapUiModelToEntity(updatedModel)
                collectionEntityRepository.updateCollectionList(
                    entity.collectionName,
                    entity.collection

                )

                show()
            }
        }


    }

    private fun mapUiModelToEntity(uiModel: CollectionsUiModel): CollectionsEntity {
        return CollectionsEntity(
            uiModel.id,
            uiModel.collectionName,
            uiModel.collection,

            )
    }

    /* fun update(colllection: CollectionsEntity) {
         viewModelScope.launch(Dispatchers.IO) {
             val db = collectionEntityRepository.getAll()
             val dbList =
                 collectionEntityRepository.getCollectionList(colllection.collectionName).collection

             val mutableDBList = dbList?.toMutableList()

             if (!mutableDBList?.contains(getValue())!!) {
                 mutableDBList?.add(getValue())
             }

             collectionEntityRepository.updateCollectionList(
                 colllection.collectionName,
                 mutableDBList
             )
             _collection.value = db
         }
     }*/

    private fun show() {
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            var UIModelList = emptyList<CollectionsUiModel>().toMutableList()
            if (UIModelList.isEmpty()) {
                UIModelList = mutableListOf()
            }
            db.forEach {
                UIModelList.add(it.toUiModel())

            }
            val colllectionList = listOf<Int>().toMutableList()

            UIModelList.forEach { collectionsUiModel ->
                if (collectionsUiModel.collection?.contains(getValue()) == true)
                    collectionsUiModel.isChecked = true

            }
            _collectionUi.value = UIModelList
        }
    }

    fun toDB() {

    }

    fun CollectionsEntity.toUiModel(isChecked: Boolean = false): CollectionsUiModel {
        return CollectionsUiModel(
            id = this.id,
            collectionName = this.collectionName,
            collection = this.collection,
            isChecked = isChecked
        )
    }


    fun CollectionsUiModel.toCollectionEntity(isChecked: Boolean = false): CollectionsEntity {
        return CollectionsEntity(
            id = this.id,
            collectionName = this.collectionName,
            collection = this.collection

        )
    }


}