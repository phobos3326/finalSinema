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
import retrofit2.Response
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

    /*var genre = 0
    var country = 0*/

    var bundle = Bundle()

    var listFilters = listOf<ModelFilter>()

    private var genre: List<ModelFilter.Genre>? = emptyList()
    private var country: List<ModelFilter.Country>? = emptyList()

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


    fun setValue(value: Int) {
        dataRepository.genreID = value
    }


    init {
        viewModelScope.launch {
            loadPremieres()
            loadTopFilms()
            pagedFilms
            // getFilters()
            //response(this@MainViewModel)
            //getPagedFilteredFilms()
            load()
            //response(this@MainViewModel)
            Log.d("FILTERED", "${pagedFilteredFilms}")

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


    val pagedFilteredFilms: Flow<PagingData<Film>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { filteredFilmPagingSource }
    ).flow.cachedIn(viewModelScope)


    /* fun isNetworkAvaibable(context: Context){
         viewModelScope.launch {
             val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
             val networkInfo = cm.activeNetworkInfo
 if (networkInfo)

         }
     }*/

    suspend fun load() {
        genre = response(this@MainViewModel).body()?.genres?.subList(0,17)
        country = response(this@MainViewModel).body()?.countries?.subList(0, 34)
        rndGenre = (0 until genre!!.size - 1).random()-1
        rndCountry = (0 until country!!.size - 1).random()-1
        rndCountryLabel = country!![rndCountry-1].country
        rndGenreLabel = genre!![rndGenre-1].genre

    }


    suspend fun getFilters(): Flow<PagingData<Film>> {

       // delay(3000)
        return if (response(this@MainViewModel).isSuccessful && genre?.size !=0 && country?.size!=0) {


            dataRepository.genreID = rndGenre
            dataRepository.countryID = rndCountry
            dataRepository.countryLabel = rndCountryLabel
            Log.d("TAG1", "${dataRepository.genreID} + ${dataRepository.countryID}" + "${dataRepository.countryLabel}\"")


            pagedFilteredFilms
        } else {
           getFilters()
        }


    }

    companion object {


        suspend fun response(mainViewModel: MainViewModel): Response<ModelFilter> {

            return mainViewModel.useCase.getFilters()
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
