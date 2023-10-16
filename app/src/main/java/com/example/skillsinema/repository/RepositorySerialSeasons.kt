package com.example.skillsinema.repository

import com.example.skillsinema.entity.ModelSeasons
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
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class RepositorySerialSeasons @Inject constructor() {

    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"



    @Provides
    suspend fun getSeasons (id: Int?): ModelSeasons {
        return retrofitSeasons.seasons(id)

    }


    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())

        .build()


    val retrofitSeasons = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceSeasons::class.java)

    interface ApiInterfaceSeasons {
        @Headers("X-API-KEY: $api_key")
        @GET("films/{id}/seasons")
        suspend fun seasons(
            @Path("id") id: Int?
        ): ModelSeasons
    }

    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
    }

}