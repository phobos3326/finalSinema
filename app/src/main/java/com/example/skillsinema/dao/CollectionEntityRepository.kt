package com.example.skillsinema.dao

import javax.inject.Inject

class CollectionEntityRepository @Inject constructor(
    private val collectionDao: CollectionDao
) {
     fun getAll(): List<CollectionsEntity> {
        return collectionDao.getAll()
    }

    suspend fun insertCollection(itemCollection:CollectionsEntity){
        collectionDao.insert(itemCollection)
    }

}