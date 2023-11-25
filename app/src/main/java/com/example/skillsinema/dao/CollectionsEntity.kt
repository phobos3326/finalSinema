package com.example.skillsinema.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class CollectionsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "collectionName")
    val collectionName: String,

    @ColumnInfo(name = "collection")
    val collection: List<String>

)
