package com.example.skillsinema.domain.common

sealed interface AppError {
    object Network : AppError
    object NotFound : AppError
    data class Unexpected(val cause: Throwable?) : AppError
}
