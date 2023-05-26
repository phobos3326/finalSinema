package com.example.skillsinema.domain

import com.example.skillsinema.data.Repository
import com.example.skillsinema.entity.BestFilmDTO
import javax.inject.Inject

class GetTopFilmsUseCase @Inject constructor(private val repository: Repository) {
    suspend fun executeTopFilm(): BestFilmDTO {
        return repository.getTopFilm()
    }

}