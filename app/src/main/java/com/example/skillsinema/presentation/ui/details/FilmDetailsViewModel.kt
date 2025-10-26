package com.example.skillsinema.presentation.ui.details

import androidx.lifecycle.viewModelScope
import com.example.skillsinema.domain.usecase.film.GetFilmDetailsUseCase
import com.example.skillsinema.domain.usecase.film.GetSimilarFilmsUseCase
import com.example.skillsinema.domain.usecase.staff.GetStaffUseCase
import com.example.skillsinema.presentation.base.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailsViewModel @Inject constructor(
    private val getDetails: GetFilmDetailsUseCase,
    private val getStaff: GetStaffUseCase,
    private val getSimilar: GetSimilarFilmsUseCase
) : BaseViewModel() {

    var state = FilmDetailsUiState()
        private set

    fun load(filmId: Int) {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            runCatching {
                val detailsDeferred = async { getDetails(filmId) }
                val staffDeferred = async { getStaff(filmId) }
                val similarDeferred = async { getSimilar(filmId) }
                Triple(detailsDeferred.await(), staffDeferred.await(), similarDeferred.await())
            }.onSuccess { (details, staff, similar) ->
                state = state.copy(details = details, staff = staff, similar = similar, isLoading = false)
            }.onFailure {
                state = state.copy(isLoading = false, error = it.message ?: "Error")
            }
        }
    }
}