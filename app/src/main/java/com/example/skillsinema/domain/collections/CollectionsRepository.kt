package com.example.skillsinema.domain.collections

import com.example.skillsinema.domain.collections.model.Collection

interface CollectionsRepository {
    suspend fun getAll(): List<Collection>
    suspend fun create(name: String): Collection
    suspend fun getByName(name: String): Collection?
    suspend fun update(collection: Collection)
    suspend fun delete(id: Int)
}
