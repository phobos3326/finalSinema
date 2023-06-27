package com.example.skillsinema.repository



import com.example.skillsinema.adapter.Film
import com.example.skillsinema.entity.Movie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class MoviePagedListRepository @Inject constructor() {
    @Provides
    suspend fun getTopFilm(page:Int):List<Film> {
        return retrofit.topList(page).films
    }
}