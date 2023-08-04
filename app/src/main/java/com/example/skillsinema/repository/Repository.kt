package com.example.skillsinema.repository


import com.example.skillsinema.BuildConfig
import com.example.skillsinema.data.BestFilmDTO
import com.example.skillsinema.data.DataDTO
import com.example.skillsinema.entity.*
import com.squareup.moshi.*
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
import java.util.*
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class Repository @Inject constructor(

) {


    private companion object {
        private const val api_key = "63101d70-3149-4782-99f8-dd1ba5fc4ab1"
    }


    @Provides
    suspend fun getPremiere(year: Int, month: String): Model {
        //delay(2000)
        return retrofitInstance().getFilms(year, month)
    }

    @Provides
    suspend fun getFilmDetails(value: Int): ModelFilmDetails {

        return retrofitInstance2().filmDetails(value)
    }

    @Provides
    suspend fun getTopFilm(): List<Film> {
        return retrofitTopFilms.topFilms().films
    }


    @Provides
    suspend fun getFilters(): List<ModelFilter.Genre> {
        return retrofitInstanceFilters().filters().genres
    }

    @Provides
    suspend fun getFilteredFilm(page: Int, countries: Int, genre: Int): List<Film> {
        return retrofit.filteredFilms(page, countries, genre, 3, 10).items
    }


    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"
    private val BASE_URL2 = "https://kinopoiskapiunofficial.tech/api/v1/"


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


    val retrofitTopFilms = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiInterfaceTopFilms::class.java)

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
        .create(ApiInterfaceFilteredFilms::class.java)



    private fun retrofitInstance(): ApiInterface = retrofit()!!.create(ApiInterface::class.java)
    private fun retrofitInstance2(): ApiInterface2 = retrofit()!!.create(ApiInterface2::class.java)
    private fun retrofitInstanceTopFilms(): ApiInterfaceTopFilms =
        retrofit()!!.create(ApiInterfaceTopFilms::class.java)

    private fun retrofitInstanceFilters(): ApiInterfaceFilters =
        retrofit()!!.create(ApiInterfaceFilters::class.java)

    private fun retrofitInstanceFilteredFilms(): ApiInterfaceFilteredFilms =
        retrofit()!!.create(ApiInterfaceFilteredFilms::class.java)

    interface ApiInterface {
        @Headers("X-API-KEY: $api_key")

        @GET("films/premieres?")
        suspend fun getFilms(
            @Query("year") year: Int,
            @Query("month") month: String

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


    interface ApiInterfaceFilters {
        @Headers("X-API-KEY: $api_key")
        @GET("films/filters")
        suspend fun filters(

        ): ModelFilter

    }

    interface ApiInterfaceFilteredFilms {
        @Headers("X-API-KEY: $api_key")
        @GET("films")
        suspend fun filteredFilms(
            @Query("page") page: Int,
            @Query("countries") countries: Int,
            @Query("genres") month: Int,
            @Query("ratingFrom") ratingFrom: Int,
            @Query("ratingTo") ratingTo: Int,

            ): ModelFilteredFilms1

    }


}