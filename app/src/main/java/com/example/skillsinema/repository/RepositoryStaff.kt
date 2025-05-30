package com.example.skillsinema.repository

import android.util.Log
import com.example.skillsinema.entity.ModelStaff
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class RepositoryStaff @Inject constructor() {

    private val BASE_URL2 = "https://kinopoiskapiunofficial.tech/api/v1/"



    @Provides
    suspend fun getStaff(id: Int): List<ModelStaff.ModelStaffItem>? {
       return retrofitStaff.staff(id).body()


    }

    @Provides
    fun provideRetrofit(): ApiInterfaceStaff {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpBuilder.build())
            .build()
            .create(ApiInterfaceStaff::class.java)
    }





    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())

        .build()







    val retrofitStaff = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {


                //  qq = it.level
                it.level = HttpLoggingInterceptor.Level.BODY

            }).build()
        )
        .baseUrl(BASE_URL2)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceStaff::class.java)


    /* private fun retrofit(): Retrofit? {
         return Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(MoshiConverterFactory.create(moshi))
             .client(httpClientBuilder.build())
             .build()
     }*/


    interface ApiInterfaceStaff {
        @Headers("X-API-KEY: $api_key")
        @GET("staff?")
        suspend fun staff(
            @Query("filmId") filmId: Int
        ): Response<List<ModelStaff.ModelStaffItem>>
    }

    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
    }


    fun parseJSON() {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        // Create Service
        val service = retrofit.create(ApiInterfaceStaff::class.java)
        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = service.staff(301)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val items = response.body()
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            // ID
                            /*val id = items[i].staffId ?: "N/A"
                            Log.d("REPOSTAFF: ", id as String)*/

                            // Employee Name
                            val employeeName = items[i].nameEn
                            Log.d("REPOSTAFF: ", employeeName)

                            // Employee Salary
                            val employeeSalary = items[i].nameRu
                            Log.d("REPOSTAFF: ", employeeSalary)

                            // Employee Age
                            val employeeAge = items[i].professionKey
                            Log.d("REPOSTAFF: ", employeeAge)

                        }
                    }

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

}


