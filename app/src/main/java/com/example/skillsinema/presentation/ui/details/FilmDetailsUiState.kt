package com.example.skillsinema.presentation.ui.details

import com.example.skillsinema.domain.model.Film
import com.example.skillsinema.domain.model.FilmDetails
import com.example.skillsinema.domain.model.Staff

data class FilmDetailsUiState(
    val details: FilmDetails? = null,
    val staff: List<Staff> = emptyList(),
    val similar: List<Film> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)