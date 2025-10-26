package com.example.skillsinema.domain.common

sealed class AppError(val message: String? = null) {
    object Network : AppError("Network error")
    object NotFound : AppError("Not found")
    data class Unknown(val error: String?) : AppError(error)

}
