package com.example.skillsinema.di

import com.example.skillsinema.data.repository.*
import com.example.skillsinema.domain.collections.CollectionsRepository
import com.example.skillsinema.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton abstract fun bindFilmRepository(impl: FilmRepositoryImpl): FilmRepository
    @Binds @Singleton abstract fun bindActorRepository(impl: ActorRepositoryImpl): ActorRepository
    @Binds @Singleton abstract fun bindStaffRepository(impl: StaffRepositoryImpl): StaffRepository
    @Binds @Singleton abstract fun bindGalleryRepository(impl: GalleryRepositoryImpl): GalleryRepository
    @Binds @Singleton abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
    @Binds @Singleton abstract fun bindDatabaseRepository(impl: DatabaseRepositoryImpl): DatabaseRepository
    @Binds @Singleton abstract fun bindFilterRepository(impl: FilterRepositoryImpl): FilterRepository
    @Binds @Singleton abstract fun bindCollectionsRepository(impl: CollectionsRepositoryImpl): CollectionsRepository
}