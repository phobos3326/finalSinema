package com.example.skillsinema.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ItemFilm::class, LikedFilms::class, WantToSeeFilm::class, CollectionsEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class ItemDataBase :RoomDatabase() {

    abstract fun itemDao():ItemDao
    abstract fun likedFilmDao():LikedFilmDao
    abstract fun wantToSeeFilmDao():WantToSeeDao

    abstract fun collectionDao():CollectionDao

    /*companion object {
        @Volatile
        private var instance: ItemDataBase? = null

        fun getDatabase(context: Context): ItemDataBase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDataBase::class.java, "item_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }*/

}