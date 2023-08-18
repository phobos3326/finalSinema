package com.example.skillsinema.repository

import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.Model
import com.example.skillsinema.entity.ModelGalerie
import com.example.skillsinema.entity.ModelKeyWord
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
class RepositoryKeyWord @Inject constructor() {

    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.1/"


    @Provides
    suspend fun getKeyWord(): List<Film> {
        // parseJSON()
        return retrofitKeyWord.keyWord("Robot",1).films
    }


    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())

        .build()


    val retrofitKeyWord = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceKeyWord::class.java)


    interface ApiInterfaceKeyWord {
        @Headers("X-API-KEY: $api_key")
        @GET("films/search-by-keyword?")
        suspend fun keyWord(
            @Query("keyword") type: String,
            @Query("page") page: Int
        ): ModelKeyWord
    }

    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
    }

}