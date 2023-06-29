package com.example.skillsinema.ui.main.home

import android.icu.text.DateFormatSymbols
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.adapter.Film
import com.example.skillsinema.data.FilmPagingSourse
import com.example.skillsinema.datasource.FilteredFilmPagingSource
import com.example.skillsinema.domain.FilteredFilmsUseCase
import com.example.skillsinema.domain.FiltersUseCase
import com.example.skillsinema.domain.GetPremiereUseCase
import com.example.skillsinema.domain.GetTopFilmsUseCase
import com.example.skillsinema.entity.*

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    // @ApplicationContext private val context: ApplicationContext,
    private var dataRepository: DataRepository,
    private val data: GetPremiereUseCase,
    private val topFilmsUseCase: GetTopFilmsUseCase,
    private val pagingSource: FilmPagingSourse,
    private val filteredFilmPagingSource: FilteredFilmPagingSource,
    private val filtersUseCase: FiltersUseCase,
    private val filteredFilmsUseCase: FilteredFilmsUseCase
    //private val navController: NavController
) : ViewModel() {
    private val _premiereModel = MutableStateFlow<List<Model.Item>>(emptyList())
    val modelPremiere = _premiereModel.asStateFlow()

    private var _topFilmModel = MutableStateFlow<List<Film>>(emptyList())
    val topFilmModel = _topFilmModel.asStateFlow()

    private var _filters = MutableStateFlow<List<ModelFilter>>(emptyList())
    val filters = _filters.asStateFlow()

    private var _filteredFilms = MutableStateFlow<List<ModelFilteredFilms1>>(emptyList())
    val filteredFilms = _filteredFilms.asStateFlow()

    var genre = 0
    var country = 0

    var bundle = Bundle()

    var listFilters = listOf<ModelFilter>()

    val pagedFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)


    init {
        viewModelScope.launch {
            loadPremieres()
            loadTopFilms()
            pagedFilms
            pagedFilteredFilms
            Log.d("FILTERED", "$pagedFilteredFilms")
        }
        // pagedFilms

    }


    private fun loadPremieres() {
        val calendar = Calendar.getInstance()
        val monthNumber = calendar.get(Calendar.MONTH)
        val monthName = DateFormatSymbols().months[monthNumber]
        val year = SimpleDateFormat("yyyy")
        val currentYear = year.format(Date())

        Log.d("MONTH", "$monthName, $currentYear")
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                data.executeGetPremiere(currentYear.toInt(), monthName).items
            }.fold(
                onSuccess = {
                    _premiereModel.value = it

                    Log.d("MainViewModel", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModel", it.message ?: "not load") }
            )
        }
    }


    private fun loadTopFilms() {

        //  flowOf(PagingData.from(listOf(Movie)).toList() == listOf(model)
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {

                topFilmsUseCase.executeTopFilm()
            }.fold(
                onSuccess = {
                    _topFilmModel.value = it

                    Log.d("MainViewModel2", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModelloadTopFilms", it.message ?: "not load") }
            )
        }


    }


  /*  fun loadFilters() {
        viewModelScope.launch {
            kotlin.runCatching {
                filtersUseCase.getFilters()
            }.fold(
                onSuccess = {
                    _filters.value = listOf(it)
                    listFilters = listOf(it)
                    Log.d("MainViewModel2", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModelFilters", it.message ?: "not load") }
            )
        }

        listFilters.forEach {
            country = it.countries[1].id
            genre = it.genres[11].id
        }

    }*/


   /* fun loadFilteredFilms() {
        viewModelScope.launch {
            kotlin.runCatching {
                filteredFilmsUseCase.getFilteredFilms()
            }.fold(
                onSuccess = {
                    _filteredFilms.value = it
                    Log.d("MainViewModelFilteredFilms", (it ?: " load").toString())
                },
                onFailure = {
                    Log.d("MainViewModelFilteredFilms", it.message ?: "not load")
                }
            )
        }
    }*/


    val pagedFilteredFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { filteredFilmPagingSource }
    ).flow.cachedIn(viewModelScope)



}


/*
@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavController(@ApplicationContext context: Context) = NavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }
}*/
