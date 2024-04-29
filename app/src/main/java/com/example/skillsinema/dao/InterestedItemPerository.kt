package com.example.skillsinema.dao

import javax.inject.Inject

class InterestedItemPerository @Inject constructor(
    private val interestedItemDao: InterestedItemDao
) {
    fun getAll():List<InterestedItemEntity>{
        return interestedItemDao.getAllInterestedItem()
    }

    suspend fun insertInterestedItem(interestedItem: InterestedItemEntity){
        interestedItemDao.insertInterestedItem(interestedItem)
    }

}