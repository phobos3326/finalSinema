package com.example.skillsinema.di

import android.content.Context
import androidx.room.Room
import com.example.skillsinema.data.local.dao.*
import com.example.skillsinema.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build() // [memory:3]

    @Provides fun provideFavoriteFilmDao(db: AppDatabase): FavoriteFilmDao = db.favoriteFilmDao() // [memory:3]
    @Provides fun provideViewedFilmDao(db: AppDatabase): ViewedFilmDao = db.viewedFilmDao() // [memory:3]
    @Provides fun provideFavoriteActorDao(db: AppDatabase): FavoriteActorDao = db.favoriteActorDao() // [memory:3]
    @Provides fun provideCollectionDao(db: AppDatabase): CollectionDao = db.collectionDao() // [memory:3]
    @Provides fun provideSearchHistoryDao(db: AppDatabase): SearchHistoryDao = db.searchHistoryDao() // [memory:3]
}