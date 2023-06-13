package com.example.skillsinema.ui.main.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skillsinema.adapter.BestFilms
import com.example.skillsinema.adapter.Film
import com.example.skillsinema.adapter.ModelFilmDetails
import com.example.skillsinema.data.FilmPagingSourse
import com.example.skillsinema.domain.GetPremiereUseCase

import com.example.skillsinema.entity.Model
import com.example.skillsinema.entity.Movie

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    // @ApplicationContext private val context: ApplicationContext,
    private val data: GetPremiereUseCase,
    //private val topFilmsUseCase: GetTopFilmsUseCase,
    private val pagingSource: FilmPagingSourse
    //private val navController: NavController
) : ViewModel() {
    private val _premiereModel = MutableStateFlow<List<Model.Item>>(emptyList())
    val modelPremiere = _premiereModel.asStateFlow()

    private val _topFilmModel = MutableStateFlow<List<Film>>(emptyList())
    val topFilmModel = _topFilmModel.asStateFlow()

    var bundle = Bundle()


   val pagedFilms :Flow<PagingData<Movie>> =Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = {pagingSource}
    ).flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            loadPremieres()
        }
       // pagedFilms

    }


    private fun loadPremieres() {
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                data.executeGetPremiere().items
            }.fold(
                onSuccess = {
                    _premiereModel.value = it
                    Log.d("MainViewModel", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModel", it.message ?: "not load") }
            )
        }
    }


  /*  private fun loadTopFilms() {
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
    }*/
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
