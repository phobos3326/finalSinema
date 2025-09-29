package com.example.skillsinema.domain.common

sealed class Result<out T> {
    data class Ok<T>(val value: T): Result<T>()
    data class Err(val error: AppError): Result<Nothing>()
}
