package com.example.skillsinema.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.skillsinema.domain.usecase.film.GetPremiereFilmsUseCase
import com.example.skillsinema.domain.usecase.film.GetTopFilmsUseCase
import com.example.skillsinema.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPremieres: GetPremiereFilmsUseCase,
    private val getTop: GetTopFilmsUseCase
) : BaseViewModel() {

    var state = HomeUiState()
        private set

    fun load(year: Int, month: String) {
        state = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            runCatching {
                val premieres = getPremieres(year, month)
                val top = getTop()
                state = state.copy(premieres = premieres, top = top, isLoading = false)
            }.onFailure {
                state = state.copy(isLoading = false, error = it.message ?: "Error")
            }
        }
    }
}