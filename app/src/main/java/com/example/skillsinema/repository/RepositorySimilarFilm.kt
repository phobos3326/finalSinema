package com.example.skillsinema.repository

import com.example.skillsinema.DataRepository
import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.entity.ModelSimilarFilm
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)

class RepositorySimilarFilm @Inject constructor(

) {

    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"



    @Provides
    suspend fun getSimilarFilm(id: Int): List<Film> {

        // parseJSON()
        return retrofitSimilarFilm.similars(id).items


    }







    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())

        .build()







    val retrofitSimilarFilm = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {

                it.level = HttpLoggingInterceptor.Level.BODY

            }).build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceSimilarFilm::class.java)


    /* private fun retrofit(): Retrofit? {
         return Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(MoshiConverterFactory.create(moshi))
             .client(httpClientBuilder.build())
             .build()
     }*/


    interface ApiInterfaceSimilarFilm {
        @Headers("X-API-KEY: $api_key")
        @GET("films/{id}/similars")
        suspend fun similars(
            @Path(value ="id") id:Int,

        ): ModelSimilarFilm
    }

    private companion object {
        private const val api_key = "c3252e89-6d89-480c-b5bd-7c97cdffdd5c"
    }


    /*fun parseJSON() {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        // Create Service

        val service = retrofit.create(ApiInterfaceGalerie::class.java)
        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = service.galerie(301,"STILL", 1)

            withContext(Dispatchers.Main) {
                if (!response.isEmpty()) {

                    val items = response
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            // ID
                            *//*val id = items[i].staffId ?: "N/A"
                            Log.d("REPOSTAFF: ", id as String)*//*

                            // Employee Name
                            val employeeName = items[i].imageUrl ?: "N/A"
                            Log.d("REPOSTAFF: ", employeeName)

                            // Employee Salary
                            val employeeSalary = items[i].imageUrl ?: "N/A"
                            Log.d("REPOSTAFF: ", employeeSalary)

                            // Employee Age
                            val employeeAge = items[i].imageUrl ?: "N/A"
                            Log.d("REPOSTAFF: ", employeeAge)

                        }
                    }

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }*/

}