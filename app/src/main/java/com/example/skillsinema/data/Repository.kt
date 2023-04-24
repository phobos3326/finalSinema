package com.example.skillsinema.data

import com.example.skillsinema.adapter.DataDTO
import com.example.skillsinema.entity.Model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class Repository @Inject constructor() {

    @Provides
    suspend fun getPremiere(): Model {
        delay(2000)
        return retrofitInstance().getFilms()
    }

    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"



    private val logInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClientBuilder = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun retrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClientBuilder.build())
            .build()
    }

    fun retrofitInstance(): ApiInterface = retrofit()!!.create(ApiInterface::class.java)


    interface ApiInterface {
        @Headers("X-API-KEY: $api_key")
        @GET("films/premieres?year=2023&month=APRIL")
        suspend fun getFilms(): DataDTO






    }

    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
    }

}