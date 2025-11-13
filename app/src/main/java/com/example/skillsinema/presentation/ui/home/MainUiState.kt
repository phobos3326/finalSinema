package com.example.skillsinema.presentation.ui.home

import com.example.skillsinema.data.model.ModelFilter
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.presentation.ui.adapters.FilmListItem

/*data class MainUiState(
    val premieres: List<Film> = emptyList(),
    val topFilms: List<Film> = emptyList(),
    val serials: List<Film> = emptyList(),
    val filteredFilms: List<Film> = emptyList(),
    val availableFilters: ModelFilter? = null,
    val selectedFilters: FilterParams? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)*/

data class MainUiState(
    val premiereItems: List<FilmListItem> = emptyList(),
    val topFilmItems: List<FilmListItem> = emptyList(),
    val serialItems: List<FilmListItem> = emptyList(),
    val filteredItems: List<FilmListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)