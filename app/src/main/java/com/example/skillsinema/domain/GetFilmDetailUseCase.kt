package com.example.skillsinema.domain

import com.example.skillsinema.data.Repository
import com.example.skillsinema.entity.FilmDTO
//import com.example.skillsinema.entity.ModelFilmDetails
import javax.inject.Inject

class GetFilmDetailUseCase @Inject constructor(private val repository: Repository) {

    suspend fun executeGetFilm(id:Int):FilmDTO{
        return repository.getFilmDetails(id)
    }
}