package com.example.skillsinema.dao

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "collections")

data class CollectionsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "collectionName")
    val collectionName: String,

    //@TypeConverters(Converter::class)

    @ColumnInfo(name = "collection")
    val collection: List<Int>,



    )


