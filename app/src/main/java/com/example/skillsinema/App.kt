package com.example.skillsinema

import android.app.Application
import androidx.room.Room
import com.example.skillsinema.dao.ItemDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){
  /*  lateinit var db: ItemDataBase
    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            ItemDataBase::class.java,
            "db"
        ).build()
    }*/
}