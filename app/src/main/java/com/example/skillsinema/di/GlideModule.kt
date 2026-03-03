package com.example.skillsinema.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class CustomGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // Создаем OkHttpClient с заголовком API ключа
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-API-KEY", "1006c25a-038b-47b4-b9f9-341f208b4ac3")
                    .build()
                chain.proceed(request)
            }
            .build()
        
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient)
        )
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
