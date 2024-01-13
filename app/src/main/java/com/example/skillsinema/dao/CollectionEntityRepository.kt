package com.example.skillsinema.dao

import androidx.room.Query
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

    suspend fun getListFilmFromCollection(name:String){
        collectionDao.getColumnByName(name).collection
    }

    suspend fun getCollectionList(id: Int): CollectionsEntity {
       return collectionDao.getCollectionList(id)
    }

    suspend fun updateCollectionList(id:Int,list:List<Int>){
        collectionDao.updateList(list, id)
    }



}