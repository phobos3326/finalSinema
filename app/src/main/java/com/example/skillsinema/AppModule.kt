package com.example.skillsinema

import android.app.Application
import android.content.Context
import com.example.skillsinema.ui.main.profile.menu.CollectionsUiModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepository {
        return DataRepository()
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideCollectionsUiModel(): CollectionsUiModel {
        return CollectionsUiModel(
            0,
            "",
            emptyList<Int>(),
            false
        ) // Replace the default values with the actual values you want to provide
    }
}

