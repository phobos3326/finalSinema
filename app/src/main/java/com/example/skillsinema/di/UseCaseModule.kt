package com.example.skillsinema.di

import com.example.skillsinema.domain.repository.*
import com.example.skillsinema.domain.usecase.actor.GetActorInfoUseCase
import com.example.skillsinema.domain.usecase.favorites.*
import com.example.skillsinema.domain.usecase.film.*
import com.example.skillsinema.domain.usecase.gallery.GetGalleryImagesUseCase
import com.example.skillsinema.domain.usecase.search.SearchFilmsUseCase
import com.example.skillsinema.domain.usecase.staff.GetStaffUseCase
import com.example.skillsinema.domain.usecase.filter.GetFiltersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides @Singleton fun provideGetPremiereFilmsUseCase(repo: FilmRepository) = GetPremiereFilmsUseCase(repo)
    @Provides @Singleton fun provideGetFilmDetailsUseCase(repo: FilmRepository) = GetFilmDetailsUseCase(repo)
    @Provides @Singleton fun provideGetTopFilmsUseCase(repo: FilmRepository) = GetTopFilmsUseCase(repo)
    @Provides @Singleton fun provideGetFilteredFilmsUseCase(repo: FilmRepository) = GetFilteredFilmsUseCase(repo)
    @Provides @Singleton fun provideGetSimilarFilmsUseCase(repo: FilmRepository) = GetSimilarFilmsUseCase(repo)
    @Provides @Singleton fun provideGetSerialsUseCase(repo: FilmRepository) = GetSerialsUseCase(repo)

    @Provides @Singleton fun provideGetActorInfoUseCase(repo: ActorRepository) = GetActorInfoUseCase(repo)
    @Provides @Singleton fun provideGetStaffUseCase(repo: StaffRepository) = GetStaffUseCase(repo)
    @Provides @Singleton fun provideGetGalleryImagesUseCase(repo: GalleryRepository) = GetGalleryImagesUseCase(repo)
    @Provides @Singleton fun provideSearchFilmsUseCase(repo: SearchRepository) = SearchFilmsUseCase(repo)
    @Provides @Singleton fun provideGetFiltersUseCase(repo: FilterRepository) = GetFiltersUseCase(repo)

    @Provides @Singleton fun provideSaveFilmToFavoritesUseCase(repo: DatabaseRepository) = SaveFilmToFavoritesUseCase(repo)
    @Provides @Singleton fun provideGetFavoriteFilmsUseCase(repo: DatabaseRepository) = GetFavoriteFilmsUseCase(repo)
    @Provides @Singleton fun provideRemoveFromFavoritesUseCase(repo: DatabaseRepository) = RemoveFromFavoritesUseCase(repo)
    @Provides @Singleton fun provideIsFilmFavoriteUseCase(repo: DatabaseRepository) = IsFilmFavoriteUseCase(repo)
}
