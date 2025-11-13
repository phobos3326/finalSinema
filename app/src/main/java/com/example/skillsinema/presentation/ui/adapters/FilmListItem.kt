package com.example.skillsinema.presentation.ui.adapters

import com.example.skillsinema.domain.model.Film

sealed class FilmListItem {
    data class FilmItem(val film: Film) : FilmListItem()
    data class ShowAllItem(val category: String) : FilmListItem()
}