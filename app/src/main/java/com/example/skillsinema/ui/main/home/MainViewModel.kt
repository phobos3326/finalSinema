package com.example.skillsinema.ui.main.home/*
package com.example.skillsinema.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.usecase.GetFilmsByCategoryUseCase
import com.example.skillsinema.domain.usecase.GetRandomFiltersUseCase
import com.example.skillsinema.presentation.base.BaseViewModel
import com.example.skillsinema.presentation.ui.adapters.FilmListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFilmsByCategory: GetFilmsByCategoryUseCase,
    private val getRandomFilters: GetRandomFiltersUseCase // ✅ Просто используем UseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            runCatching {
                // ✅ UseCase сам генерирует фильтры
                getFilmsByCategory.loadAll()

                combine(
                    getFilmsByCategory("premieres"),
                    getFilmsByCategory("top_films"),
                    getFilmsByCategory("serials"),
                    getFilmsByCategory("filtered")
                ) { premieres, topFilms, serials, filtered ->
                    MainDataResult(
                        premieres = premieres,
                        topFilms = topFilms,
                        serials = serials,
                        filtered = filtered
                    )
                }.collect { result ->
                    val premiereItems = prepareFilmList(result.premieres, "premieres")
                    val topFilmItems = prepareFilmList(result.topFilms, "top_films")
                    val serialItems = prepareFilmList(result.serials, "serials")
                    val filteredItems = prepareFilmList(result.filtered, "filtered")

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
*/
