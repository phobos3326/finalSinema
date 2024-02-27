package com.example.skillsinema.dao

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.io.Serializable


class Converter {

    @TypeConverter
    fun fromString(value: String?): List<Int>? {
       return if (value == null) {
            listOf<Int>()
        } else {
            val type = object : TypeToken<List<Int>>() {}.type
            return Gson().fromJson(value, Array<Int>::class.java)?.toList()
        }



    }

    @TypeConverter
    fun fromArray(list: List<Int>?): String? {
        return if (list?.isEmpty() == true){
            null
        } else {
            Gson().toJson(list)
        }
    }


  /*  @TypeConverter
    fun fromArray(array: List<Int>): String? {
        // Преобразуем массив в строку формата JSON
        return if (array.isEmpty()) {
            null
        } else {
            Gson().toJson(array)
        }


    }*/


    /* @TypeConverter
    fun toList(json: String?): List<Int>? {
        val type = object : TypeToken<List<Int>?>() {}.type
        return Gson().fromJson(json, type)
    }*/

   /* @TypeConverter
    fun toStringArrayList(value: String): List<Int> {
        return try {
            Gson().fromJson<List<Int>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }*/

 /*   @TypeConverter
    fun toStringArrayList(json: String): List<Int> {

        return if (json.isNullOrEmpty()) {
            listOf()
        } else {
            val type = object : TypeToken<String>() {}.type
            Gson().fromJson(json, type)
        }


        // Преобразуем строку формата JSON обратно в массив
        // return Gson().fromJson(arrayString, object: TypeToken<ArrayList<String>>() {}.type)
    }*/

}

/*
class Converter {

    @TypeConverter
    fun listToJson(value: List<Int>?): String? {
        return Gson().toJson(value).toString()
    }

    @TypeConverter
    fun jsonToList(value: String): List<Int> {
        return Gson().fromJson(value, Array<Int>::class.java).toList()
    }
}*/




