package com.example.skillsinema.ui.main.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.adapter.BestFilms
import com.example.skillsinema.domain.GetPremiereUseCase
import com.example.skillsinema.domain.GetTopFilmsUseCase
import com.example.skillsinema.entity.Model

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    // @ApplicationContext private val context: ApplicationContext,
    private val data: GetPremiereUseCase,
    private val topFilmsUseCase: GetTopFilmsUseCase
    //private val navController: NavController
) : ViewModel() {
    private val _premiereModel = MutableStateFlow<List<Model.Item>>(emptyList())
    val modelPremiere = _premiereModel.asStateFlow()

    private val _topFilmModel = MutableStateFlow<List<BestFilms.Film>>(emptyList())
    val topFilmModel = _topFilmModel.asStateFlow()

    var bundle = Bundle()

    init {
        viewModelScope.launch {
            loadPremieres()

        }

        viewModelScope.launch {
            loadTopFilms()
        }
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


    private fun loadTopFilms() {
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                topFilmsUseCase.executeTopFilm().films
            }.fold(
                onSuccess = {
                    _topFilmModel.value = it
                    Log.d("MainViewModel2", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModelloadTopFilms", it.message ?: "not load") }
            )
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
