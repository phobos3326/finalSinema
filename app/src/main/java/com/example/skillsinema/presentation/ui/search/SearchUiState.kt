package com.example.skillsinema.presentation.ui.search

import com.example.skillsinema.domain.model.Film

data class SearchUiState(
    val results: List<Film> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)