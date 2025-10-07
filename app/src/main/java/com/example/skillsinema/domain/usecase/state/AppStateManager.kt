package com.example.skillsinema.domain.usecase.state

import com.example.skillsinema.domain.model.AppState
import com.example.skillsinema.domain.model.FilterParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppStateManager @Inject constructor() {

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    fun updateSelectedFilm(filmId: Int) {
        _appState.value = _appState.value.copy(selectedFilmId = filmId)
    }

    fun updateSelectedActor(actorId: Int) {
        _appState.value = _appState.value.copy(selectedActorId = actorId)
    }

    fun updateImageType(imageType: String) {
        _appState.value = _appState.value.copy(imageType = imageType)
    }

    fun updateFilterParams(filterParams: FilterParams) {
        _appState.value = _appState.value.copy(filterParams = filterParams)
    }

    fun updateSearchQuery(query: String) {
        _appState.value = _appState.value.copy(searchQuery = query)
    }

    fun updateSelectedSeries(seriesId: Int?) {
        _appState.value = _appState.value.copy(selectedSeriesId = seriesId)
    }

    fun getCurrentState(): AppState = _appState.value
}