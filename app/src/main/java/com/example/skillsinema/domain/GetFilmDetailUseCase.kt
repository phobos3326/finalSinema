package com.example.skillsinema.domain

import com.example.skillsinema.entity.ModelFilmDetails
import com.example.skillsinema.repository.Repository
//import com.example.skillsinema.entity.ModelFilmDetails
import javax.inject.Inject

class GetFilmDetailUseCase @Inject constructor(private val repository: Repository) {

    suspend fun executeGetFilm(id:Int): ModelFilmDetails {
        return repository.getFilmDetails(id)
    }
}