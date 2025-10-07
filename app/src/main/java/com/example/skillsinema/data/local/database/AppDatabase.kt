package com.example.skillsinema.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.skillsinema.data.local.dao.*
import com.example.skillsinema.data.local.entity.*
import com.example.skillsinema.data.local.converter.Converters

@Database(
    entities = [
        FavoriteFilmEntity::class,
        ViewedFilmEntity::class,
        FavoriteActorEntity::class,
        CollectionEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteFilmDao(): FavoriteFilmDao
    abstract fun viewedFilmDao(): ViewedFilmDao
    abstract fun favoriteActorDao(): FavoriteActorDao
    abstract fun collectionDao(): CollectionDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        const val DATABASE_NAME = "skillsinema_database"
    }
}