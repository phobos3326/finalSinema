package com.example.skillsinema

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.skillsinema.dao.ItemDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

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