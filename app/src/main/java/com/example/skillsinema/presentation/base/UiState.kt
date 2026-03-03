package com.example.skillsinema.presentation.base

/**
 * Базовое UI состояние для всех экранов
 */
interface UiState {
    val isLoading: Boolean
    val error: String?
}

/**
 * Базовое UI состояние с загрузкой и ошибкой
 */
open class BaseUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null
) : UiState
