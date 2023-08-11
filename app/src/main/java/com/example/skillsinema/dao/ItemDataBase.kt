package com.example.skillsinema.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemFilm::class], version = 1)
abstract class ItemDataBase :RoomDatabase() {

    abstract fun itemDao():ItemDao

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