package com.example.skillsinema

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/*
@Module
@InstallIn(SingletonComponent::class)
object ContextModule {

    @Provides
    @ApplicationContext // Use a qualifier if necessary
    fun provideApplicationContext(application: Application): Context {
        return application
    }
}*/
