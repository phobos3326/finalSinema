package com.example.skillsinema.repository

import com.example.skillsinema.DataRepository
import com.example.skillsinema.adapter.Film
import com.example.skillsinema.data.BestFilmDTO
import com.example.skillsinema.data.DataDTO
//import com.example.skillsinema.adapter.FilmDTO
import com.example.skillsinema.entity.*
//import com.example.skillsinema.entity.ModelFilmDetails

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
class Repository @Inject constructor(

) {




    @Provides
    suspend fun getPremiere(year: Int, month: String): Model {
        //delay(2000)
        return retrofitInstance().getFilms(year,month)
    }

    @Provides
    suspend fun getFilmDetails(value: Int): ModelFilmDetails {

        return retrofitInstance2().filmDetails(value)
    }

    @Provides
    suspend fun getTopFilm(): List<Film> {
        return retrofitInstanceTopFilms().topFilms().films
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


    private fun retrofitInstance(): ApiInterface = retrofit()!!.create(ApiInterface::class.java)
    private fun retrofitInstance2(): ApiInterface2 = retrofit()!!.create(ApiInterface2::class.java)
    private fun retrofitInstanceTopFilms(): ApiInterfaceTopFilms =
        retrofit()!!.create(ApiInterfaceTopFilms::class.java)


    interface ApiInterface {
        @Headers("X-API-KEY: $api_key")

        @GET("films/premieres?")
        suspend fun getFilms(
            @Query("year") year:Int,
            @Query("month") month:String

        ): DataDTO


    }


    interface ApiInterface2 {
        @Headers("X-API-KEY: $api_key")
        @GET("films/{id}")
        suspend fun filmDetails(
            @Path(value = "id") id: Int
            // @Query("id") id:Int
        ): ModelFilmDetails

    }

    interface ApiInterfaceTopFilms {
        @Headers("X-API-KEY: $api_key")
        @GET("films/top?type=TOP_250_BEST_FILMS&page=1")
        suspend fun topFilms(
            // @Query("page") page: Int
        ): BestFilmDTO

    }

    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
    }

}