package com.example.skillsinema.presentation.base

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    data class Navigate(val destinationId: Int) : UiEvent()
}