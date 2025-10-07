package com.example.skillsinema.di

import com.example.skillsinema.data.repository.*
import com.example.skillsinema.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton abstract fun bindFilmRepository(impl: FilmRepositoryImpl): FilmRepository // [memory:3]
    @Binds @Singleton abstract fun bindActorRepository(impl: ActorRepositoryImpl): ActorRepository // [memory:3]
    @Binds @Singleton abstract fun bindStaffRepository(impl: StaffRepositoryImpl): StaffRepository // [memory:3]
    @Binds @Singleton abstract fun bindGalleryRepository(impl: GalleryRepositoryImpl): GalleryRepository // [memory:3]
    @Binds @Singleton abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository // [memory:3]
    @Binds @Singleton abstract fun bindDatabaseRepository(impl: DatabaseRepositoryImpl): DatabaseRepository // [memory:3]
    @Binds @Singleton abstract fun bindFilterRepository(impl: FilterRepositoryImpl): FilterRepository // [memory:3]
}