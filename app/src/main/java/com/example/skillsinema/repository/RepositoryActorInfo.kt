package com.example.skillsinema.repository

import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelActorInfo
import com.example.skillsinema.entity.ModelGalerie
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
class RepositoryActorInfo @Inject constructor() {


    private val BASE_URL2 = "https://kinopoiskapiunofficial.tech/api/v1/"


    @Provides
    suspend fun getActor(id: Int): ModelActorInfo {
        // parseJSON()
        return retrofitActor.actor(id)


    }

    @Provides
    suspend fun getActorFilm(id: Int): List<Film> {
        // parseJSON()
        return retrofitActor.actor(id).films


    }

    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())

        .build()


    val retrofitActor = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {

                it.level = HttpLoggingInterceptor.Level.BODY

            }).build()
        )
        .baseUrl(BASE_URL2)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceActor::class.java)


    /* private fun retrofit(): Retrofit? {
         return Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(MoshiConverterFactory.create(moshi))
             .client(httpClientBuilder.build())
             .build()
     }*/


    interface ApiInterfaceActor {
        @Headers("X-API-KEY: $api_key")
        @GET("staff/{id}")
        suspend fun actor(
            @Path(value = "id") id: Int,

        ): ModelActorInfo
    }

    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
    }



}