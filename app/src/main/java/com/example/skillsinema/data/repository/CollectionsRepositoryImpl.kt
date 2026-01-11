package com.example.skillsinema.repository

import com.example.skillsinema.dao.CollectionEntityRepository
import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.domain.collections.CollectionsRepository
import com.example.skillsinema.domain.collections.model.Collection
import com.example.skillsinema.repository.mappers.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CollectionsRepositoryImpl @Inject constructor(
    private val roomRepo: CollectionEntityRepository
) : CollectionsRepository {

    override suspend fun getAll(): List<Collection> = withContext(Dispatchers.IO) {
        roomRepo.getAll().map { it.toDomain() }
    }

    override suspend fun create(name: String): Collection = withContext(Dispatchers.IO) {
        roomRepo.insertCollection(
            CollectionsEntity(
                id = 0,
                collectionName = name,
                collection = emptyList()
            )
        )
        // Room вернёт id только через insert-return-id, у тебя сейчас insert void,
        // поэтому просто возвращаем актуальную сущность по имени:
        roomRepo.getCollectionList(name).toDomain()
    }

    override suspend fun getByName(name: String): Collection? = withContext(Dispatchers.IO) {
        runCatching { roomRepo.getCollectionList(name).toDomain() }.getOrNull()
    }

    override suspend fun update(collection: Collection) = withContext(Dispatchers.IO) {
        roomRepo.updateCollectionList(collection.name, collection.filmIds)
    }

    override suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        // У тебя delete принимает CollectionsEntity, поэтому находим и удаляем по id
        val all = roomRepo.getAll()
        val entity = all.firstOrNull { it.id == id } ?: return@withContext
        roomRepo.delete(entity)
    }
}
