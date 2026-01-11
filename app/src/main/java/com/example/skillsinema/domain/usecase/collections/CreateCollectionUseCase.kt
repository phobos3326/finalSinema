package com.example.skillsinema.domain.collections.usecase

import com.example.skillsinema.domain.collections.CollectionsRepository
import javax.inject.Inject

class CreateCollectionUseCase @Inject constructor(
    private val repo: CollectionsRepository
) {
    suspend operator fun invoke(name: String) {
        repo.create(name)
    }
}
