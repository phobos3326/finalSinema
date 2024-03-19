package com.example.skillsinema.ui.main.home

import android.app.Application

import android.icu.text.DateFormatSymbols
import android.icu.text.SimpleDateFormat

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.dao.ItemDao

import com.example.skillsinema.dao.ItemFilm
import com.example.skillsinema.dao.ItemRepository

import com.example.skillsinema.entity.Film
import com.example.skillsinema.datasource.FilmPagingSourse
import com.example.skillsinema.datasource.FilteredFilmPagingSource
import com.example.skillsinema.datasource.SerialsPagingSourse
import com.example.skillsinema.domain.FilteredFilmsUsecase
import com.example.skillsinema.domain.FiltersUseCase
import com.example.skillsinema.domain.GetPremiereUseCase
import com.example.skillsinema.domain.GetSeasonsUseCase
import com.example.skillsinema.domain.GetTopFilmsUseCase
import com.example.skillsinema.entity.*

import com.example.skillsinema.repository.RepositoryStaff


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers


import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import retrofit2.Response
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    //  @ApplicationContext private val context: ApplicationContext,
    private var dataRepository: DataRepository,
    private val data: GetPremiereUseCase,
    private val topFilmsUseCase: GetTopFilmsUseCase,
    private val pagingSource: FilmPagingSourse,
    private val filteredFilmPagingSource: FilteredFilmPagingSource,
    private val filtersUseCase: FiltersUseCase,
    private val filteredFilmsUseCase: FilteredFilmsUsecase,
    private val staff: RepositoryStaff,
    private val useCase: FiltersUseCase,
    private val itemRepository: ItemRepository,
    private val itemDao: ItemDao,
    private val serialsPagingSourse: SerialsPagingSourse,
    private val seasonsUseCase: GetSeasonsUseCase,


    application: Application


    //private val navController: NavController
) : AndroidViewModel(application) {
    private val _premiereModel = MutableStateFlow<List<Film>>(emptyList())
    val modelPremiere = _premiereModel.asStateFlow()

    private var _topFilmModel = MutableStateFlow<List<Film>>(emptyList())
    val topFilmModel = _topFilmModel.asStateFlow()

    private var _seasons = MutableStateFlow<List<ModelSeasons.Item>>(emptyList())
    val seasons = _seasons.asStateFlow()

    private var _filters = MutableStateFlow<List<ModelFilter>>(emptyList())
    val filters = _filters.asStateFlow()

    private var _filteredFilms = MutableStateFlow<List<ModelFilteredFilms1>>(emptyList())
    val filteredFilms = _filteredFilms.asStateFlow()



    var bundle = Bundle()

    var listFilters = listOf<ModelFilter>()

    private var genre: List<ModelFilter.Genre>? = emptyList()
    private var country: List<ModelFilter.Country>? = emptyList()

    var rndGenre = 0
    var rndCountry = 0
    var rndCountryLabel = ""
    var rndGenreLabel = ""


    init {
        viewModelScope.launch {
            loadPremieres()
            loadTopFilms()
            pagedFilms
            load()
            Log.d("FILTERED", "${pagedFilteredFilms}")
        }
    }

    fun insertItem(id: Int) {
        viewModelScope.launch {
            itemRepository.insertItem((ItemFilm(id = id)))
        }
    }

    fun setValue(value: Int) {
        dataRepository.genreID = value
    }

    val pagedFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)


    private fun loadPremieres() {
        val calendar = Calendar.getInstance()
        val monthNumber = calendar.get(Calendar.MONTH)
        val monthName = DateFormatSymbols(Locale.ENGLISH).months[monthNumber]
        val year = SimpleDateFormat("yyyy")
        val currentYear = year.format(Date())

        Log.d("MONTH", "$monthName, $currentYear")
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                data.executeGetPremiere(currentYear.toInt(), monthName)
            }.fold(
                onSuccess = {
                     val a =it

                    _premiereModel.value = it.subList(0,19)

                    Log.d(TAG, it.toString())
                },
                onFailure = { Log.d(TAG, it.message ?: "not load") }
            )
        }
    }


    private fun loadTopFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                topFilmsUseCase.executeTopFilm()
            }.fold(
                onSuccess = {
                    _topFilmModel.value = isViewed(it)
                },
                onFailure = { Log.d(TAG, it.message ?: "not load") }
            )
        }
    }


    private fun loadSeasons() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                seasonsUseCase.getSeasons()
            }.fold(
                onSuccess = {
                    _seasons.value = it.items
                },
                onFailure = { Log.d(TAG, it.message ?: "not load") }
            )
        }
    }


    fun isViewed(listFilm: List<Film>): List<Film> {
        val a = listFilm
        val db = itemDao.getAll()
        db.forEach { filmID ->
            a.forEach { Film ->
                if (Film.filmId == filmID.id || Film.kinopoiskId == filmID.id) {
                    Film.isViewed = true
                }
            }
        }
        Log.d(TAG, "LIST FILM  $a")
        return a
    }



    private val pagedFilteredFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { filteredFilmPagingSource }
    ).flow.cachedIn(viewModelScope)

    val serials: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { serialsPagingSourse }
    ).flow.cachedIn(viewModelScope)

    suspend fun load() {
        genre = response(this@MainViewModel).body()?.genres?.subList(0, 17)
        country = response(this@MainViewModel).body()?.countries?.subList(0, 34)
        rndGenre = (0 until genre!!.size).random() + 1
        dataRepository.rndGenre=rndGenre
        rndCountry = (0 until country!!.size).random() + 1
        dataRepository.rndCountry=rndCountry
        rndCountryLabel = country!![rndCountry - 1].country
        dataRepository.rndCountryLabel = rndCountryLabel
        rndGenreLabel = genre!![rndGenre - 1].genre
        dataRepository.rndGenreLabel = rndGenreLabel
        Log.d(
            TAG,
            "genre: ${genre},\n  country: ${country},\n rndGenre: ${rndGenre}, \n rndCountry: ${rndCountry}, \n rndCountryLabel: ${rndCountryLabel},\n rndGenreLabel: ${rndGenreLabel}"
        )
    }


    suspend fun getFilters(): Flow<PagingData<Film>> {
        return if (response(this@MainViewModel).isSuccessful && genre?.size != 0 && country?.size != 0) {
            dataRepository.genreID = rndGenre
            dataRepository.countryID = rndCountry
            dataRepository.countryLabel = rndCountryLabel
            Log.d(
                TAG,
                "${dataRepository.genreID} + ${dataRepository.countryID} + ${dataRepository.countryLabel}"
            )
            pagedFilteredFilms
        } else {
            getFilters()
        }
    }

    companion object {
        val TAG = "MainViewModel"
        suspend fun response(mainViewModel: MainViewModel): Response<ModelFilter> {
            return mainViewModel.useCase.getFilters()
        }
    }


}


