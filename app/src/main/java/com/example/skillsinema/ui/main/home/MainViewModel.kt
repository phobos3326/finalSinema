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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex.Empty

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

    private var _topFilmModel = MutableStateFlow<List<Any?>>(emptyList())
    val topFilmModel = _topFilmModel.asStateFlow()

    var bundle = Bundle()
    //var listMovie: List<Movie> = emptyList()


    val pagedFilms: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true

        ),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)

    /*  fun getPagedFilms(): Flow<PagingData<Movie>> {
          return Pager(
              config = PagingConfig(pageSize = 20),
              pagingSourceFactory = { pagingSource }
          ).flow.cachedIn(viewModelScope)
      }*/

    suspend fun aa(): MutableList<Movie> {
        val listMovie: MutableList<Movie> = emptyList<Movie>().toMutableList()
        repeat(5) {
            pagedFilms.map { pd ->
                _topFilmModel.value = (pd.toList())

            }
            Log.d("TTT", "${listMovie.size}")
        }
        return listMovie
    }

    val pagedFilms1: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { pagingSource }
    ).flow.cachedIn(viewModelScope)


    @Suppress("UNCHECKED_CAST")
    private suspend fun <T : Any> PagingData<T>.toList(): List<T> {
        val flow = PagingData::class.java.getDeclaredField("flow").apply {
            isAccessible = true
        }.get(this) as Flow<Any?>
        val pageEventInsert = flow.single()
        val pageEventInsertClass = Class.forName("androidx.paging.PageEvent\$Insert")
        val pagesField = pageEventInsertClass.getDeclaredField("pages").apply {
            isAccessible = true
        }
        val pages = pagesField.get(pageEventInsert) as List<Any?>
        val transformablePageDataField =
            Class.forName("androidx.paging.TransformablePage").getDeclaredField("data").apply {
                isAccessible = true
            }
        val listItems =
            pages.flatMap { transformablePageDataField.get(it) as List<*> }

        return listItems as List<T>
    }


    init {
        viewModelScope.launch {
            loadPremieres()
            aa()
            pagedFilms
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


    private fun loadTopFilms() {

        //  flowOf(PagingData.from(listOf(Movie)).toList() == listOf(model)
        //navController.navigate(R.id.action_mainFragment_to_itemInfoFragment, bundle)
        /*viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                topFilmsUseCase.executeTopFilm()
            }.fold(
                onSuccess = {
                    _topFilmModel.value = it
                    Log.d("MainViewModel2", (it ?: " load").toString())
                },
                onFailure = { Log.d("MainViewModelloadTopFilms", it.message ?: "not load") }
            )
        }*/


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
