package com.example.skillsinema.repository

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
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class FilmInCiollection @Inject constructor(){

    private companion object {
        private const val api_key = "1006c25a-038b-47b4-b9f9-341f208b4ac3"
    }

    @Provides
    suspend fun getFilmFromId(id:Int){}


    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    //     /api/v1/staff/{id}

    val retrofit = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(Repository.ApiInterfaceFilteredFilms::class.java)


  /*  interface ApiInterface{
        @Headers("X-API-KEY: $api_key")
        @GET()
    }*/



}