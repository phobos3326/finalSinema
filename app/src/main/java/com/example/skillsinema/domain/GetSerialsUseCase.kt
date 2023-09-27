package com.example.skillsinema.domain

import com.example.skillsinema.entity.Film
import com.example.skillsinema.repository.RepositoryKeyWord
import javax.inject.Inject

class GetSerialsUseCase @Inject constructor(
    private val repositoryKeyWord: RepositoryKeyWord
) {
    suspend fun getSerials(page: Int): List<Film> {


        return repositoryKeyWord.getSeries(
            1,
            1,
            null,
            "TV_SERIES",
            7,
            10,
            1990,
            3000,
            null,
            "",
            page

        )

    }

}