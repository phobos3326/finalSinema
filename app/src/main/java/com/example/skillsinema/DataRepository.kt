package com.example.skillsinema

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Scope
import javax.inject.Singleton

@MyScope

data class DataRepository @Inject constructor(var id:Int) {


   // var id: Int = 0


    fun getValue(): Int {
        return id
    }

   /* fun setValue(value: Int) {
        id = value
    }*/


}