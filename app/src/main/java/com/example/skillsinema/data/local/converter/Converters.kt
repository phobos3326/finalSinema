package com.example.skillsinema.data.local.converter

import androidx.room.TypeConverter
import com.example.skillsinema.domain.model.Genre
import com.example.skillsinema.domain.model.Country
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Collections.emptyList

class Converters {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return list?.joinToString(separator = ",") ?: ""
    }

    @TypeConverter
    fun toIntList(data: String?): List<Int> {
        return if (data.isNullOrEmpty()) emptyList()
        else data.split(",").mapNotNull { it.toIntOrNull() }
    }

    @TypeConverter
    fun fromGenreList(value: List<Genre>): String {
        val type = Types.newParameterizedType(List::class.java, Genre::class.java)
        val adapter: JsonAdapter<List<Genre>> = moshi.adapter(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toGenreList(value: String): List<Genre> {
        val type = Types.newParameterizedType(List::class.java, Genre::class.java)
        val adapter: JsonAdapter<List<Genre>> = moshi.adapter(type)
        return adapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun fromCountryList(value: List<Country>): String {
        val type = Types.newParameterizedType(List::class.java, Country::class.java)
        val adapter: JsonAdapter<List<Country>> = moshi.adapter(type)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toCountryList(value: String): List<Country> {
        val type = Types.newParameterizedType(List::class.java, Country::class.java)
        val adapter: JsonAdapter<List<Country>> = moshi.adapter(type)
        return adapter.fromJson(value) ?: emptyList()
    }
}