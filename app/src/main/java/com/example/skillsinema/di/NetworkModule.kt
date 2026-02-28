package com.example.skillsinema.di

import com.example.skillsinema.data.api.KinopoiskApi
import com.example.skillsinema.data.api.GalleryApi
import com.example.skillsinema.data.api.ActorApi
import com.example.skillsinema.data.api.SearchApi
import com.example.skillsinema.data.api.StaffApi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"
    private const val API_KEY = "1006c25a-038b-47b4-b9f9-341f208b4ac3" // Лучше хранить в BuildConfig

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-API-KEY", API_KEY)
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        apiKeyInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(GsonConverterFactory.create()) // Если нужен Gson
            .build()
    }

    @Provides
    @Singleton
    fun provideKinopoiskApi(retrofit: Retrofit): KinopoiskApi {
        return retrofit.create(KinopoiskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGalleryApi(retrofit: Retrofit): GalleryApi {
        return retrofit.create(GalleryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideActorApi(retrofit: Retrofit): ActorApi {
        return retrofit.create(ActorApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStaffApi(retrofit: Retrofit): StaffApi {
        return retrofit.create(StaffApi::class.java)
    }
}

