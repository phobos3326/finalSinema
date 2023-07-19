package com.example.skillsinema.domain

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.ModelActorInfo
import com.example.skillsinema.repository.RepositoryActorInfo
import javax.inject.Inject

class GetActorUseCase @Inject constructor(
    private val repositoryActorInfo: RepositoryActorInfo,
    private val dataRepository: DataRepository
) {
    suspend fun getActor(): ModelActorInfo {
        val id = dataRepository.idActor
        return repositoryActorInfo.getActor(id)
    }
}