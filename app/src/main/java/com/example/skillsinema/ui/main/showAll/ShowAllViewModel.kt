package com.example.skillsinema.ui.main.showAll

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.*
import com.example.skillsinema.DataRepository
import com.example.skillsinema.R
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
import com.example.skillsinema.domain.GetPremiereUseCase
import com.example.skillsinema.domain.GetSeasonsUseCase
import com.example.skillsinema.domain.GetTopFilmsUseCase
import com.example.skillsinema.entity.ModelFilter
import com.example.skillsinema.repository.RepositoryStaff
import com.example.skillsinema.ui.main.ItemInfo.StateItemFilmInfo
import com.example.skillsinema.ui.main.home.MainViewModel
import com.example.skillsinema.ui.main.home.RVDataType
import com.example.skillsinema.ui.main.home.TypeOfAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private var pagingSource: FilmPagingSourse,
    private val itemRepository: ItemRepository,
    private val serialsPagingSourse: SerialsPagingSourse,

    private var dataRepository: DataRepository,


    private val filteredFilmPagingSource: FilteredFilmPagingSourceAll,

    private val useCase: FiltersUseCase,

    private val itemDao: ItemDao,




    application: Application
) : ViewModel() {

    private var _state = MutableStateFlow<TypeOfAdapter>(TypeOfAdapter.WITHOUTPAGING)
    val state = _state.asStateFlow()

    private val _stateRVDataType = MutableStateFlow<RVDataType>(RVDataType.SERIALS)
    val stateRVDataType = _stateRVDataType.asStateFlow()

    private var genre: List<ModelFilter.Genre>? = emptyList()
    private var country: List<ModelFilter.Country>? = emptyList()

    init {
        viewModelScope.launch {
            pagedFilms
            load()
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



  //  var adapterType:TypeOfAdapter? = null
  //  var rvType:RVDataType? = null

    fun setState(adapterType:TypeOfAdapter){
        _state.value= adapterType!!
    }


    fun setStateType(rvType:RVDataType){
        _stateRVDataType.value= rvType!!
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




    companion object {
        val TAG = "MainViewModel"
        suspend fun response(showAllViewModel: ShowAllViewModel): Response<ModelFilter> {
            return showAllViewModel.useCase.getFilters()
        }
    }

}