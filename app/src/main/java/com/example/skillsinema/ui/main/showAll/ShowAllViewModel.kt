package com.example.skillsinema.ui.main.showAll

import android.app.Application
import android.icu.text.DateFormatSymbols
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.*
import androidx.viewpager.widget.PagerTitleStrip
import com.example.skillsinema.DataRepository
import com.example.skillsinema.R
import com.example.skillsinema.dao.CollectionEntityRepository
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.dao.ItemDao
import com.example.skillsinema.dao.ItemFilm
import com.example.skillsinema.dao.ItemRepository
import com.example.skillsinema.entity.Film
import com.example.skillsinema.datasource.FilmPagingSourse
import com.example.skillsinema.datasource.FilteredFilmPagingSource
import com.example.skillsinema.datasource.FilteredFilmPagingSourceAll
import com.example.skillsinema.datasource.SerialsPagingSourse
import com.example.skillsinema.domain.FilteredFilmsUsecase
import com.example.skillsinema.domain.FiltersUseCase
import com.example.skillsinema.domain.GetFilmDetailUseCase
import com.example.skillsinema.domain.GetPremiereUseCase
import com.example.skillsinema.domain.GetSeasonsUseCase
import com.example.skillsinema.domain.GetTopFilmsUseCase
import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.entity.ModelFilter
import com.example.skillsinema.repository.RepositoryStaff
import com.example.skillsinema.ui.main.ItemInfo.StateItemFilmInfo
import com.example.skillsinema.ui.main.home.MainViewModel
import com.example.skillsinema.ui.main.home.RVDataType
import com.example.skillsinema.ui.main.home.TypeOfAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private var pagingSource: FilmPagingSourse,
    private val itemRepository: ItemRepository,
    private val serialsPagingSourse: SerialsPagingSourse,
    private val data: GetPremiereUseCase,
    private var dataRepository: DataRepository,
    private var getFilm: GetFilmDetailUseCase,
    private var collectionEntityRepository: CollectionEntityRepository,

    private val filteredFilmPagingSource: FilteredFilmPagingSourceAll,

    private val useCase: FiltersUseCase,

    private val itemDao: ItemDao,


    ) : ViewModel() {

    private var _state = MutableStateFlow<TypeOfAdapter>(TypeOfAdapter.WITHOUTPAGING)
    val state = _state.asStateFlow()

    private val _stateRVDataType = MutableStateFlow<RVDataType>(RVDataType.SERIALS)
    val stateRVDataType = _stateRVDataType.asStateFlow()

    private val _premiereModel = MutableStateFlow<List<Film>>(emptyList())
    val modelPremiere = _premiereModel.asStateFlow()

    private val _collection = MutableStateFlow<List<Film>>(emptyList())
    val collection = _collection.asStateFlow()

    private var genre: List<ModelFilter.Genre>? = emptyList()
    private var country: List<ModelFilter.Country>? = emptyList()
    private var collectionList: List<Int>? = emptyList()

    init {
        viewModelScope.launch {
            pagedFilms
            load()
            loadPremieres()
        }
    }

    var rndGenre = 0
    var rndCountry = 0
    var rndCountryLabel = ""
    var rndGenreLabel = ""

    val pagedFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)


    fun insertItem(id: Int) {
        viewModelScope.launch {
            itemRepository.insertItem((ItemFilm(id = id)))
        }
    }

    private fun loadPremieres() {
        val calendar = Calendar.getInstance()
        val monthNumber = calendar.get(Calendar.MONTH)
        val monthName = DateFormatSymbols(Locale.ENGLISH).months[monthNumber]
        val year = SimpleDateFormat("yyyy")
        val currentYear = year.format(Date())

        Log.d("MONTH", "$monthName, $currentYear")

        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                data.executeGetPremiere(currentYear.toInt(), monthName)
            }.fold(
                onSuccess = {
                    _premiereModel.value = it

                    Log.d(MainViewModel.TAG, it.toString())
                },
                onFailure = { Log.d(MainViewModel.TAG, it.message ?: "not load") }
            )
        }
    }

    //  var adapterType:TypeOfAdapter? = null
    //  var rvType:RVDataType? = null

    fun setState(adapterType: TypeOfAdapter) {
        _state.value = adapterType!!
    }


    fun setStateType(rvType: RVDataType?) {
        if (rvType != null) {
            _stateRVDataType.value = rvType
        }
    }

    val serials: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { serialsPagingSourse }
    ).flow.cachedIn(viewModelScope)


    private val pagedFilteredFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { filteredFilmPagingSource }
    ).flow.cachedIn(viewModelScope)

    suspend fun load() {
        genre = response(this@ShowAllViewModel).body()?.genres?.subList(0, 17)
        country = response(this@ShowAllViewModel).body()?.countries?.subList(0, 34)
        rndGenre = dataRepository.rndGenre
        rndCountry = dataRepository.rndCountry
        rndCountryLabel = dataRepository.rndCountryLabel
        rndGenreLabel = dataRepository.rndGenreLabel
        Log.d(
            ShowAllViewModel.TAG,
            "genre: ${genre},\n  country: ${country},\n rndGenre: ${rndGenre}, \n rndCountry: ${rndCountry}, \n rndCountryLabel: ${rndCountryLabel},\n rndGenreLabel: ${rndGenreLabel}"
        )

    }

    suspend fun getFilters(): Flow<PagingData<Film>> {
        return if (ShowAllViewModel.response(this@ShowAllViewModel).isSuccessful && genre?.size != 0 && country?.size != 0) {
            dataRepository.genreID = rndGenre
            dataRepository.countryID = rndCountry
            dataRepository.countryLabel = rndCountryLabel
            Log.d(
                MainViewModel.TAG,
                "${dataRepository.genreID} + ${dataRepository.countryID} + ${dataRepository.countryLabel}"
            )
            pagedFilteredFilms
        } else {
            getFilters()
        }
    }


    /* private fun loadFilmsInCollection() {
         viewModelScope.launch(Dispatchers.IO) {
             kotlin.runCatching {
                 topFilmsUseCase.executeTopFilm()
             }.fold(
                 onSuccess = {
                     _topFilmModel.value = isViewed(it)
                 },
                 onFailure = { Log.d(MainViewModel.TAG, it.message ?: "not load") }
             )
         }
     }*/


    fun showCollection(name: String?) {
        val listFilm = mutableListOf<Film>()
        viewModelScope.launch(Dispatchers.IO) {
            val db = collectionEntityRepository.getAll()
            db.forEach { collectionEntity ->
                if (collectionEntity.collectionName == name) {
                    collectionList = collectionEntity.collection
                    collectionList?.forEach {
                        val filmDetail = getFilm.executeGetFilm(it)

                        listFilm.add(filmDetail.toFilm())

                    }
                }
                _collection.value = listFilm
            }
        }
    }


    fun ModelFilmDetails.toFilm(): Film {
        return Film(
            countries = this.countries,
            description = description,
            filmId = this.kinopoiskId,
            filmLength = this.filmLength.toString(),
            genres = this.genres,
            nameEn = this.nameEn,
            nameRu = this.nameRu,
            posterUrl = this.posterUrl,
            posterUrlPreview = this.posterUrlPreview,
            rating = this.ratingKinopoisk.toString(),
            ratingVoteCount = this.ratingKinopoiskVoteCount,
            type = this.type,
            year = this.year.toString(),
            kinopoiskId = this.kinopoiskId,
            ratingImdb = this.ratingImdb.toString(),
            isViewed = null,
            isLiked = null


        )
    }

    companion object {
        val TAG = "MainViewModel"
        suspend fun response(showAllViewModel: ShowAllViewModel): Response<ModelFilter> {
            return showAllViewModel.useCase.getFilters()
        }
    }

}