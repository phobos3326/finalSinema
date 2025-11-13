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

    var state = MainUiState()
        private set

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
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            runCatching {
                val premieres = getPremieres(year, month)
                val topFilms = getTopFilms()
                val serials = getSerials()
                val filters = getFilters()
                val randomFilters = getRandomFilters()
                val filteredFilms = getFilteredFilms(randomFilters)

                // ← БИЗНЕС-ЛОГИКА В ViewModel:
                val premiereItems = prepareFilmList(premieres, "premieres")
                val topFilmItems = prepareFilmList(topFilms, "top_films")
                val serialItems = prepareFilmList(serials, "serials")
                val filteredItems = prepareFilmList(filteredFilms, "filtered")

                state = state.copy(
                    premiereItems = premiereItems,
                    topFilmItems = topFilmItems,
                    serialItems = serialItems,
                    filteredItems = filteredItems,
                    isLoading = false
                )
            }
                .onFailure {
                    state = state.copy(
                        isLoading = false,
                        error = it.message ?: "Error loading data"
                    )
                }
        }
    }

    private fun prepareFilmList(films: List<Film>, category: String): List<FilmListItem> {
        return films.take(20).map { FilmListItem.FilmItem(it) } +
                FilmListItem.ShowAllItem(category)
    }

    fun refresh() {
        loadData()
    }
}

data class Quintuple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)