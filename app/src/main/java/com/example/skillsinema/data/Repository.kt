package com.example.skillsinema.data

import com.example.skillsinema.entity.ModelPremiere
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class Repository {


    suspend fun getPremiere(): ModelPremiere {
        delay(2000)
        return retrofitInstance().getPremiere()
    }

    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2"


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun retrofit(): Retrofit?{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun retrofitInstance(): ApiInterface = retrofit()!!.create(ApiInterface::class.java)


    interface ApiInterface{
        @GET("films/premieres?year=2023&month=APRIL")
        suspend fun getPremiere():ModelPremiere


    }

}