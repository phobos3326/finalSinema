package com.example.skillsinema.domain.collections.usecase

import com.example.skillsinema.domain.collections.CollectionsRepository
import javax.inject.Inject

class ToggleFilmInCollectionUseCase @Inject constructor(
    private val repo: CollectionsRepository
) {
    suspend operator fun invoke(collectionName: String, filmId: Int): Boolean {
        val collection = repo.getByName(collectionName) ?: return false
        val list = collection.filmIds.toMutableList()

        val nowChecked = if (list.contains(filmId)) {
            list.remove(filmId); false
        } else {
            list.add(filmId); true
        }

        repo.update(collection.copy(filmIds = list))
        return nowChecked
    }
}
