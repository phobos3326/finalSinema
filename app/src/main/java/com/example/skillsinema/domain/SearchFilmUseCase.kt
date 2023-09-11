package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.RepositoryKeyWord
import javax.inject.Inject

class searchFilmUseCase @Inject constructor(
    private val repository: RepositoryKeyWord,
    private val dataRepository: DataRepository
) {

    suspend fun getKeyWord(page:Int, word:String

    ): List<Film> {

        return repository.getKeyWord(
            dataRepository.countries,
            dataRepository.genre,
            dataRepository.order,
            dataRepository.filmType,
            dataRepository.ratingFrom,
            dataRepository.ratingTo,
            dataRepository.yearFrom,
            dataRepository.yearTo,
            dataRepository.imdbId,
            word,
            page
        )
    }

}