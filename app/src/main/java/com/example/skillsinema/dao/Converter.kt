package com.example.skillsinema.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {


    @TypeConverter
    fun fromArray(array: List<String>): String? {
        // Преобразуем массив в строку формата JSON
        return if (array.isEmpty()) {
            null
        } else {
            Gson().toJson(array)
        }


    }

    @TypeConverter
    fun toStringArrayList(value: String): List<String> {
        return try {
            Gson().fromJson<List<String>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    /*   @TypeConverter
    fun toArray(json: String): List<String> {

        return if (json.isNullOrEmpty()) {
            listOf()
        } else {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(json, type)
        }


        // Преобразуем строку формата JSON обратно в массив
        // return Gson().fromJson(arrayString, object: TypeToken<ArrayList<String>>() {}.type)
    }
}*/
}