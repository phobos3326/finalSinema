package com.example.skillsinema.repository


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
class GalerieRepository @Inject constructor() {

    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"


    @Provides
    suspend fun getGalerieStill(page: Int, id: Int): List<ModelGalerie.Item> {
        // parseJSON()
        return retrofitGalerie.galerie(id, "STILL", page).items.toList()
    }

    @Provides
    suspend fun getGalerieShooting(page: Int, id: Int): List<ModelGalerie.Item> {
        // parseJSON()
        return retrofitGalerie.galerie(id, "SHOOTING", page).items.toList()
    }

    @Provides
    suspend fun getGalerieWALLPAPER(page: Int, id: Int): List<ModelGalerie.Item> {
        // parseJSON()
        return retrofitGalerie.galerie(id, "WALLPAPER", page).items.toList()
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



    interface ApiInterfaceGalerie {
        @Headers("X-API-KEY: $api_key")
        @GET("films/{id}/images")
        suspend fun galerie(
            @Path(value = "id") id: Int,
            @Query("type") type: String,
            @Query("page") page: Int
        ): ModelGalerie
    }

    private companion object {
        private const val api_key = "c3252e89-6d89-480c-b5bd-7c97cdffdd5c"
    }



}