package com.example.skillsinema.di

import com.example.skillsinema.domain.repository.*
import com.example.skillsinema.domain.usecase.actor.GetActorInfoUseCase
import com.example.skillsinema.domain.usecase.favorites.*
import com.example.skillsinema.domain.usecase.film.*
import com.example.skillsinema.domain.usecase.gallery.GetGalleryImagesUseCase
import com.example.skillsinema.domain.usecase.search.SearchFilmsUseCase
import com.example.skillsinema.domain.usecase.staff.GetStaffUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides @Singleton fun provideGetPremiereFilmsUseCase(repo: FilmRepository) = GetPremiereFilmsUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideGetFilmDetailsUseCase(repo: FilmRepository) = GetFilmDetailsUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideGetTopFilmsUseCase(repo: FilmRepository) = GetTopFilmsUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideGetFilteredFilmsUseCase(repo: FilmRepository) = GetFilteredFilmsUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideGetSimilarFilmsUseCase(repo: FilmRepository) = GetSimilarFilmsUseCase(repo) // [memory:3]

    @Provides @Singleton fun provideGetActorInfoUseCase(repo: ActorRepository) = GetActorInfoUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideGetStaffUseCase(repo: StaffRepository) = GetStaffUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideGetGalleryImagesUseCase(repo: GalleryRepository) = GetGalleryImagesUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideSearchFilmsUseCase(repo: SearchRepository) = SearchFilmsUseCase(repo) // [memory:3]

    @Provides @Singleton fun provideSaveFilmToFavoritesUseCase(repo: DatabaseRepository) = SaveFilmToFavoritesUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideGetFavoriteFilmsUseCase(repo: DatabaseRepository) = GetFavoriteFilmsUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideRemoveFromFavoritesUseCase(repo: DatabaseRepository) = RemoveFromFavoritesUseCase(repo) // [memory:3]
    @Provides @Singleton fun provideIsFilmFavoriteUseCase(repo: DatabaseRepository) = IsFilmFavoriteUseCase(repo) // [memory:3]
}