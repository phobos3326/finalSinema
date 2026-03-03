package com.example.skillsinema.presentation.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.usecase.GetFilmsByCategoryUseCase
import com.example.skillsinema.presentation.base.BaseViewModel
import com.example.skillsinema.presentation.ui.adapters.FilmListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFilmsByCategory: GetFilmsByCategoryUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        Log.d("MainViewModel", "loadData started")
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            runCatching {
                // ✅ Загружаем все данные один раз
                Log.d("MainViewModel", "loadAll started")
                getFilmsByCategory.loadAll()

                // ✅ Подписываемся на все потоки
                combine(
                    getFilmsByCategory("premieres"),
                    getFilmsByCategory("top_films"),
                    getFilmsByCategory("serials"),
                    getFilmsByCategory("filtered")
                ) { premieres, topFilms, serials, filtered ->
                    Log.d("MainViewModel", "combine received: premieres=${premieres.size}, top=${topFilms.size}, serials=${serials.size}, filtered=${filtered.size}")
                    MainDataResult(
                        premieres = premieres,
                        topFilms = topFilms,
                        serials = serials,
                        filtered = filtered
                    )
                }.collect { result ->
                    Log.d("MainViewModel", "collect received result")
                    val premiereItems = prepareFilmList(result.premieres, "premieres")
                    val topFilmItems = prepareFilmList(result.topFilms, "top_films")
                    val serialItems = prepareFilmList(result.serials, "serials")
                    val filteredItems = prepareFilmList(result.filtered, "filtered")

                    Log.d("MainViewModel", "submitting state: premiereItems=${premiereItems.size}, topFilmItems=${topFilmItems.size}")
                    _state.value = MainUiState(
                        premiereItems = premiereItems,
                        topFilmItems = topFilmItems,
                        serialItems = serialItems,
                        filteredItems = filteredItems,
                        isLoading = false,
                        error = null
                    )
                }
            }.onFailure { exception ->
                Log.e("MainViewModel", "loadData failed", exception)
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Ошибка загрузки данных"
                )
            }
        }
    }

    private fun prepareFilmList(films: List<Film>, category: String): List<FilmListItem> {
        return if (films.isNotEmpty()) {
            films.take(20).map { FilmListItem.FilmItem(it) } +
                    FilmListItem.ShowAllItem(category)
        } else {
            emptyList()
        }
    }

    fun refresh() {
        loadData()
    }
}

private data class MainDataResult(
    val premieres: List<Film>,
    val topFilms: List<Film>,
    val serials: List<Film>,
    val filtered: List<Film>
)
