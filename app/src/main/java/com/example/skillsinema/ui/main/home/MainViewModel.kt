package com.example.skillsinema.ui.main.home

import android.content.Context
import android.icu.text.DateFormatSymbols
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.Film
import com.example.skillsinema.datasource.FilmPagingSourse
import com.example.skillsinema.datasource.FilteredFilmPagingSource
import com.example.skillsinema.domain.FilteredFilmsUseCase
import com.example.skillsinema.domain.FiltersUseCase
import com.example.skillsinema.domain.GetPremiereUseCase
import com.example.skillsinema.domain.GetTopFilmsUseCase
import com.example.skillsinema.entity.*
import com.example.skillsinema.repository.RepositoryStaff
import dagger.hilt.android.internal.Contexts

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
    private val filteredFilmsUseCase: FilteredFilmsUseCase,
    private val staff: RepositoryStaff,
    private val useCase: FiltersUseCase


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


    fun setValue(value: Int) {
        dataRepository.genreID = value
    }

    init {
        viewModelScope.launch {
            loadPremieres()
            loadTopFilms()
            pagedFilms
            // getFilters()

            //getPagedFilteredFilms()
            Log.d("FILTERED", "${getPagedFilteredFilms()}")

            //staff.parseJSON()
        }
        // pagedFilms

    }


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
                data.executeGetPremiere(currentYear.toInt(), monthName).items
            }.fold(
                onSuccess = {
                    _premiereModel.value = it

                    Log.d("MainViewModel", it.toString())
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

                    Log.d("MainViewModel2", it.toString())
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


    fun getPagedFilteredFilms(): Flow<PagingData<Film>> {
        // getFilters()

        return Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { filteredFilmPagingSource }
        ).flow.cachedIn(viewModelScope)
    }


    /* fun isNetworkAvaibable(context: Context){
         viewModelScope.launch {
             val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
             val networkInfo = cm.activeNetworkInfo
 if (networkInfo)

         }
     }*/

    suspend fun getFilters(): Flow<PagingData<Film>> {

        /*val listGenre = useCase.getFiltersGenre()
            dataRepository.genreID = listGenre[(0..listGenre.size - 1).random()].id


            val listCountry = useCase.getFiltersCountries()
            val rndCountryID = listCountry[(0..listCountry.size - 1).random()].id
            setValue(rndCountryID)
            dataRepository.countryLabel = listCountry[rndCountryID].country*/


        val response = useCase.getFilters()
      return  if (response.isSuccessful) {
            val genre = response.body()?.genres
            val country = response.body()?.countries

            val rndGenre = (0 until genre!!.size).random()
            val rndCountry = (0 until country!!.size).random()

            dataRepository.genreID = rndGenre
            dataRepository.countryID = rndCountry
             Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
                pagingSourceFactory = { filteredFilmPagingSource }
            ).flow.cachedIn(viewModelScope)
        } else {
          Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
              pagingSourceFactory = { filteredFilmPagingSource }
          ).flow.cachedIn(viewModelScope)
      }


    }





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
