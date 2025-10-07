package com.example.skillsinema.domain.usecase.actor

import com.example.skillsinema.domain.model.Actor
import com.example.skillsinema.domain.repository.ActorRepository
import javax.inject.Inject

class GetActorInfoUseCase @Inject constructor(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(actorId: Int): Actor {
        return actorRepository.getActorInfo(actorId)
    }
}