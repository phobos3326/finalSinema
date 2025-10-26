package com.example.skillsinema.presentation.ui.home

import com.example.skillsinema.domain.model.Film

data class HomeUiState(
    val premieres: List<Film> = emptyList(),
    val top: List<Film> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)