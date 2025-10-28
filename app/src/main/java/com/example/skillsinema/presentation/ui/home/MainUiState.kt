package com.example.skillsinema.presentation.ui.home

import com.example.skillsinema.data.model.ModelFilter
import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilterParams

data class MainUiState(
    val premieres: List<Film> = emptyList(),
    val topFilms: List<Film> = emptyList(),
    val serials: List<Film> = emptyList(),
    val filteredFilms: List<Film> = emptyList(),
    val availableFilters: ModelFilter? = null,       // ← Доступные фильтры (жанры, страны)
    val selectedFilters: FilterParams? = null,       // ← Выбранные фильтры для поиска
    val isLoading: Boolean = false,
    val error: String? = null
)