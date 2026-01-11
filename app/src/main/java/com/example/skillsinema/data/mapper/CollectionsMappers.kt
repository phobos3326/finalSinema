package com.example.skillsinema.repository.mappers

import com.example.skillsinema.dao.CollectionsEntity
import com.example.skillsinema.domain.collections.model.Collection

fun CollectionsEntity.toDomain(): Collection =
    Collection(
        id = id,
        name = collectionName,
        filmIds = collection ?: emptyList()
    )

fun Collection.toEntity(): CollectionsEntity =
    CollectionsEntity(
        id = id,
        collectionName = name,
        collection = filmIds
    )
