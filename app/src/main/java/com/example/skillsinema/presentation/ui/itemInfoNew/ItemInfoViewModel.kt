package com.example.skillsinema.presentation.ui.itemInfoNew

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.dao.LikedFilmRepository
import com.example.skillsinema.dao.LikedFilms
import com.example.skillsinema.domain.LoadItemToDB
import com.example.skillsinema.domain.collections.usecase.CreateCollectionUseCase
import com.example.skillsinema.domain.collections.usecase.GetCollectionsUiUseCase
import com.example.skillsinema.domain.collections.usecase.ToggleFilmInCollectionUseCase
import com.example.skillsinema.domain.model.GalleryImage
import com.example.skillsinema.domain.usecase.film.GetFilmDetailsUseCase
import com.example.skillsinema.domain.usecase.film.GetSimilarFilmsUseCase
import com.example.skillsinema.domain.usecase.gallery.GetGalleryImagesUseCase
import com.example.skillsinema.domain.usecase.staff.GetStaffUseCase
import androidx.lifecycle.MutableLiveData
import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.entity.ModelStaff
import com.example.skillsinema.presentation.base.BaseViewModel
import com.example.skillsinema.presentation.ui.itemInfo.StateItemFilmInfo
import com.example.skillsinema.presentation.ui.model.TypeItem
import com.example.skillsinema.presentation.ui.profile.menu.CollectionsUiModel
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
    private val getFilmDetails: GetFilmDetailsUseCase,
    private val getStaff: GetStaffUseCase,
    private val getGalleryImages: GetGalleryImagesUseCase,
    private val getSimilarFilms: GetSimilarFilmsUseCase,
    private val dataRepository: DataRepository,
    private val likedFilmRepository: LikedFilmRepository,
    private val loadItemToDB: LoadItemToDB,
    private val getCollectionsUi: GetCollectionsUiUseCase,
    private val createCollection: CreateCollectionUseCase,
    private val toggleFilmInCollection: ToggleFilmInCollectionUseCase,
) : BaseViewModel() {

    private var started = false

    // ===== Film LiveData (для совместимости с Fragment) =====
    private val _film = MutableLiveData<ModelFilmDetails>()
    val film = _film

    // ===== Staff state =====
    private val _staff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val staff = _staff.asStateFlow()

    private val _noActorStaff = MutableStateFlow<List<ModelStaff.ModelStaffItem>>(emptyList())
    val noActorStaff = _noActorStaff.asStateFlow()

    // ===== Similar films =====
    private val _similar = MutableStateFlow<List<Film>>(emptyList())
    val similar = _similar.asStateFlow()

    // ===== Gallery =====
    private val _pagedGalerie = MutableStateFlow<Flow<PagingData<GalleryImage>>?>(null)
    val pagedGalerie = _pagedGalerie.asStateFlow()

    // ===== Screen state =====
    private val _state = MutableStateFlow<StateItemFilmInfo>(StateItemFilmInfo.FilmState)
    val state = _state.asStateFlow()

    // ===== Liked state =====
    private val _isLikedState = MutableStateFlow(false)
    val isLikedState = _isLikedState.asStateFlow()

    // ===== Collections =====
    private val _collectionUi = MutableStateFlow<List<CollectionsUiModel>>(emptyList())
    val collectionUi = _collectionUi.asStateFlow()

    fun start(filmId: Int) {
        if (started) return
        if (filmId <= 0) return
        started = true
        setValue(filmId)

        _pagedGalerie.value = getGalleryImages(filmId, "STILL").cachedIn(viewModelScope)

        viewModelScope.launch {
            loadFilm(filmId)
            loadStaff(filmId)
            loadSimilarFilms(filmId)
            _state.value = StateItemFilmInfo.FilmState
            refreshCollections()
        }
    }

    fun setValue(filmId: Int) {
        dataRepository.id = filmId
    }

    fun getValue(): Int = dataRepository.id

    fun setSeriesValue(value: Int) {
        dataRepository.seriesID = value
    }

    // ===== Collections =====
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

    // ===== Liked =====
    fun insertItemIsLiked(id: Int) {
        viewModelScope.launch {
            if (!_isLikedState.value) {
                _isLikedState.value = true
                likedFilmRepository.insertLikedFilm(LikedFilms(id = id))
            } else {
                _isLikedState.value = false
                likedFilmRepository.delete(LikedFilms(id = id))
            }
        }
    }

    // ===== DB =====
    fun isertItemToDb(type: TypeItem, id: Int) {
        viewModelScope.launch {
            loadItemToDB.getItemToDB(type, id)
        }
    }

    // ===== Loaders =====
    suspend fun loadFilm(filmId: Int) {
        viewModelScope.launch {
            runCatching {
                getFilmDetails(filmId)
            }.fold(
                onSuccess = { details ->
                    // Маппинг domain.model.FilmDetails -> entity.ModelFilmDetails
                    _film.postValue(ModelFilmDetails(
                        kinopoiskId = details.kinopoiskId,
                        nameRu = details.nameRu,
                        nameEn = details.nameEn,
                        nameOriginal = details.nameOriginal,
                        year = details.year,
                        description = details.description,
                        shortDescription = details.shortDescription,
                        posterUrl = details.posterUrl,
                        posterUrlPreview = details.posterUrlPreview,
                        coverUrl = details.coverUrl,
                        logoUrl = details.logoUrl,
                        genres = details.genres.map { com.example.skillsinema.entity.Film.Genre(it.genre) },
                        countries = details.countries.map { com.example.skillsinema.entity.Film.Country(it.country) },
                        ratingKinopoisk = details.ratingKinopoisk,
                        ratingImdb = details.ratingImdb,
                        filmLength = details.filmLength,
                        slogan = details.slogan,
                        ratingAgeLimits = details.ageLimit,
                        startYear = details.startYear,
                        endYear = details.endYear,
                        serial = details.serial,
                        completed = details.completed,
                        has3D = null,
                        hasImax = null,
                        imdbId = null,
                        isTicketsAvailable = null,
                        lastSync = null,
                        editorAnnotation = null,
                        productionStatus = null,
                        ratingAwait = details.ratingAwait,
                        ratingAwaitCount = null,
                        ratingFilmCritics = details.ratingFilmCritics,
                        ratingFilmCriticsVoteCount = null,
                        ratingGoodReview = null,
                        ratingGoodReviewVoteCount = null,
                        ratingImdbVoteCount = null,
                        ratingKinopoiskVoteCount = null,
                        ratingMpaa = null,
                        ratingRfCritics = null,
                        ratingRfCriticsVoteCount = null,
                        reviewsCount = null,
                        shortFilm = null,
                        type = null,
                        webUrl = null,
                        isLiked = false
                    ))
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
                    Log.d("ItemInfoViewModel", it.message ?: "not load film")
                }
            )
        }
    }

    private fun loadStaff(filmId: Int) {
        viewModelScope.launch {
            runCatching {
                getStaff(filmId)
            }.fold(
                onSuccess = { list ->
                    val actorList = mutableListOf<ModelStaff.ModelStaffItem>()
                    val noActorList = mutableListOf<ModelStaff.ModelStaffItem>()
                    list.forEach { item ->
                        val mapped = ModelStaff.ModelStaffItem(
                            staffId = item.staffId,
                            nameRu = item.nameRu ?: "",
                            nameEn = item.nameEn ?: "",
                            description = item.description,
                            posterUrl = item.posterUrl ?: "",
                            professionText = item.professionText ?: "",
                            professionKey = item.professionKey ?: ""
                        )
                        if (item.professionKey == "ACTOR") actorList.add(mapped)
                        else noActorList.add(mapped)
                    }
                    _staff.value = actorList
                    _noActorStaff.value = noActorList
                },
                onFailure = {
                    Log.d("ItemInfoViewModel", it.message ?: "not load staff")
                }
            )
        }
    }

    private fun loadSimilarFilms(filmId: Int) {
        viewModelScope.launch {
            runCatching {
                getSimilarFilms(filmId)
            }.fold(
                onSuccess = { list ->
                    _similar.value = list.map { domainFilm ->
                        Film(
                            kinopoiskId = domainFilm.kinopoiskId,
                            nameRu = domainFilm.nameRu,
                            nameEn = domainFilm.nameEn,
                            year = domainFilm.year,
                            rating = domainFilm.rating?.toString(),
                            posterUrl = domainFilm.posterUrl,
                            posterUrlPreview = domainFilm.posterUrlPreview,
                            genres = domainFilm.genres.map { Film.Genre(it.genre) },
                            countries = domainFilm.countries.map { Film.Country(it.country) },
                            filmId = null,
                            filmLength = null,
                            ratingVoteCount = null,
                            type = null,
                            ratingImdb = null,
                            isViewed = false,
                            isLiked = false,
                            description = null
                        )
                    }
                },
                onFailure = {
                    Log.d("ItemInfoViewModel", it.message ?: "not load similar")
                }
            )
        }
    }
}






