package com.example.skillsinema.dao

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun providesItemDB(@ApplicationContext context:Context): ItemDataBase{
        return Room.databaseBuilder(context, ItemDataBase::class.java, "DB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesItemDao(itemDB:ItemDataBase): ItemDao{
        return itemDB.itemDao()
    }

    @Provides
    fun providesLikedFilmDao(itemDB: ItemDataBase):LikedFilmDao{
        return itemDB.likedFilmDao()
    }

    @Provides
    fun provideWantToSeeFilmDao(itemDB: ItemDataBase) : WantToSeeDao{
        return  itemDB.wantToSeeFilmDao()
    }

    @Provides
    fun provideCollectionsDao(itemDB: ItemDataBase) : CollectionDao{
        return  itemDB.collectionDao()
    }
}

