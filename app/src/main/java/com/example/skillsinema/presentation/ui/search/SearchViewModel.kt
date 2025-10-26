package com.example.skillsinema.presentation.ui.search

import androidx.lifecycle.viewModelScope
import com.example.skillsinema.domain.model.FilterParams
import com.example.skillsinema.domain.usecase.film.GetFilteredFilmsUseCase
import com.example.skillsinema.domain.usecase.search.SearchFilmsUseCase
import com.example.skillsinema.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchFilms: SearchFilmsUseCase,
    private val getFiltered: GetFilteredFilmsUseCase
) : BaseViewModel() {

    var state = SearchUiState()
        private set

    private var debounceJob: Job? = null

    fun onQueryChanged(query: String) {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(300)
            executeSearch(query)
        }
    }

    private fun executeSearch(query: String) {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            runCatching { searchFilms(query) }
                .onSuccess { state = state.copy(results = it, isLoading = false) }
                .onFailure { state = state.copy(isLoading = false, error = it.message) }
        }
    }

    fun filter(params: FilterParams) {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            runCatching { getFiltered(params) }
                .onSuccess { state = state.copy(results = it, isLoading = false) }
                .onFailure { state = state.copy(isLoading = false, error = it.message) }
        }
    }
}