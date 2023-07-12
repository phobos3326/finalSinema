package com.example.skillsinema.repository



import android.util.Log
import com.example.skillsinema.entity.Model
import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.entity.ModelStaff
import com.squareup.moshi.Moshi
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
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class GalerieRepository @Inject constructor() {

    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"



    @Provides
    suspend fun getGalerie(): List<ModelGalerie.Item> {
       // parseJSON()
        return retrofitGalerie.galerie(301,"STILL", 1).items.toList()


    }







    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())

        .build()







    val retrofitGalerie = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {

                it.level = HttpLoggingInterceptor.Level.BODY

            }).build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceGalerie::class.java)


    /* private fun retrofit(): Retrofit? {
         return Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(MoshiConverterFactory.create(moshi))
             .client(httpClientBuilder.build())
             .build()
     }*/


    interface ApiInterfaceGalerie {
        @Headers("X-API-KEY: $api_key")
        @GET("films/{id}/images")
        suspend fun galerie(
            @Path(value ="id") id:Int,
            @Query("type") type: String,
            @Query("page") page:Int
        ): ModelGalerie
    }

    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
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