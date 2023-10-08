package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.ModelSeasons
import com.example.skillsinema.repository.RepositorySerialSeasons
import javax.inject.Inject

class GetSeasonsUseCase @Inject constructor(
    private val repositorySerialSeasons: RepositorySerialSeasons,
    private val dataRepository: DataRepository
) {

    suspend fun getSeasons(): ModelSeasons {
        return repositorySerialSeasons.getSeasons(dataRepository.seriesID)
    }

}