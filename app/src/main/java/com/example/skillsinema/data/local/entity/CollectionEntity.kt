package com.example.skillsinema.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections")
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val filmIds: List<Int>,
    val createdAt: Long = System.currentTimeMillis()
)