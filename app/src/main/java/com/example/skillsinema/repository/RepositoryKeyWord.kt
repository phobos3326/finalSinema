package com.example.skillsinema.repository

import com.example.skillsinema.entity.Film
import com.example.skillsinema.entity.ModelKeyWord
import com.example.skillsinema.entity.ModelVariousFilters
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
import retrofit2.http.Query
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class RepositoryKeyWord @Inject constructor() {

    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"


    @Provides
    suspend fun getKeyWord(countries: Int?, genres: Int?, oreder: String?, type: String?, ratingFrom: Int?, ratingTo: Int?, yearFrom: Int?, yearTo: Int?, imdbId: String?, keyword: String?, page:Int): List<Film> {
            // parseJSON()
        return retrofitKeyWord.keyWord(countries, genres, oreder, type, ratingFrom, ratingTo, yearFrom, yearTo, imdbId, keyword, page).items
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
        @GET("films?")
        suspend fun keyWord(
            @Query("countries") countries: Int?,
            @Query("genres") genres: Int?,
            @Query("order") order: String?,
            @Query("type") type: String?,
            @Query("ratingFrom") ratingFrom: Int?,
            @Query("ratingTo") ratingTo: Int?,
            @Query("yearFrom") yearFrom: Int?,
            @Query("yearTo") yearTo: Int?,
            @Query("imdbId") imdbId: String?,
            @Query("keyword") keyword: String?,
            @Query("page") page: Int?
        ): ModelVariousFilters
    }

    private companion object {
        private const val api_key = "1006c25a-038b-47b4-b9f9-341f208b4ac3"
    }

}