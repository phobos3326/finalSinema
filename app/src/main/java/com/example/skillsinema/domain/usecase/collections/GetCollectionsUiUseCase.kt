package com.example.skillsinema.domain.collections.usecase

import com.example.skillsinema.domain.collections.CollectionsRepository
import com.example.skillsinema.presentation.ui.profile.menu.CollectionsUiModel
import javax.inject.Inject

class GetCollectionsUiUseCase @Inject constructor(
    private val repo: CollectionsRepository
) {
    suspend operator fun invoke(currentFilmId: Int): List<CollectionsUiModel> {
        return repo.getAll().map { c ->
            CollectionsUiModel(
                id = c.id,
                collectionName = c.name,
                collection = c.filmIds,
                isChecked = c.filmIds.contains(currentFilmId)
            )
        }
    }
}
