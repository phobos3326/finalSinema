package com.example.skillsinema.domain

import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.Repository
import javax.inject.Inject

class GetTopFilmsUseCase @Inject constructor(private val repository: Repository) {
    suspend fun executeTopFilm():List<Film> {
        return repository.getTopFilm()
    }

}
