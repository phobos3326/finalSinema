package com.example.skillsinema.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.skillsinema.data.model.ModelFilter
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.usecase.film.GetFilteredFilmsUseCase
import com.example.skillsinema.domain.usecase.film.GetPremiereFilmsUseCase
import com.example.skillsinema.domain.usecase.film.GetSerialsUseCase
import com.example.skillsinema.domain.usecase.film.GetTopFilmsUseCase
import com.example.skillsinema.domain.usecase.filter.GetFiltersUseCase
import com.example.skillsinema.domain.usecase.filter.GetRandomFiltersUseCase
import com.example.skillsinema.presentation.base.BaseViewModel
import com.example.skillsinema.presentation.base.UiEvent
import com.example.skillsinema.presentation.ui.adapters.FilmListItem
import com.example.skillsinema.repository.Repository_GetFiltersFactory.getFilters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject



@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPremieres: GetPremiereFilmsUseCase,
    private val getTopFilms: GetTopFilmsUseCase,
    private val getSerials: GetSerialsUseCase,
    private val getFilters: GetFiltersUseCase,
    private val getRandomFilters: GetRandomFiltersUseCase,
    private val getFilteredFilms: GetFilteredFilmsUseCase
) : BaseViewModel() {

    // ✅ИСПОЛЬЗУЕМ StateFlow вместо var
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        val calendar = Calendar.getInstance()
        val monthNumber = calendar.get(Calendar.MONTH)
        val monthName = DateFormatSymbols(Locale.ENGLISH).months[monthNumber]
        val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toInt()

        load(year, monthName)
    }

    fun load(year: Int, month: String) {
        // Обновляем через _state.value
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            runCatching {
                // Загружаем все данные
                val premieres = getPremieres(year, month)
                val topFilms = getTopFilms()
                val serials = getSerials()
                val filters = getFilters()
                val randomFilters = getRandomFilters()
                val filteredFilms = getFilteredFilms(randomFilters)

                // Подготавливаем списки с кнопкой "Показать все"
                val premiereItems = prepareFilmList(premieres, "premieres")
                val topFilmItems = prepareFilmList(topFilms, "top_films")
                val serialItems = prepareFilmList(serials, "serials")
                val filteredItems = prepareFilmList(filteredFilms, "filtered")

                // ✅ Обновляем state
                _state.value = MainUiState(
                    premiereItems = premiereItems,
                    topFilmItems = topFilmItems,
                    serialItems = serialItems,
                    filteredItems = filteredItems,
                    isLoading = false,
                    error = null
                )
            }.onFailure { exception ->
                //  Обрабатываем ошибку
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = exception.message ?: "Ошибка загрузки данных"
                )
            }
        }
    }

    //  Подготовка списка: первые 20 фильмов + кнопка "Показать все"
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

// ✅ State содержит готовые списки FilmListItem
data class MainUiState(
    val premiereItems: List<FilmListItem> = emptyList(),
    val topFilmItems: List<FilmListItem> = emptyList(),
    val serialItems: List<FilmListItem> = emptyList(),
    val filteredItems: List<FilmListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)