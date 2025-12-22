package com.example.skillsinema.repository


import com.example.skillsinema.entity.PagedMovieList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.time.Month
import java.time.Year

interface MovieListApi {

  /*  @Headers("X-API-KEY: $api_key")
    @GET("films/premieres")
    suspend fun movies(@Query("year") year: Int, @Query("month") month: String): MovieList*/

    @Headers("X-API-KEY: $api_key")
    @GET("films/top?type=TOP_250_BEST_FILMS")
    suspend fun topList(
        @Query("page") page: Int
    ): PagedMovieList


    private companion object {
        private const val api_key = "1006c25a-038b-47b4-b9f9-341f208b4ac3"
    }

}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(MovieListApi::class.java)