package com.example.skillsinema.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.skillsinema.entity.Film
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM viewedItem")
    fun getAll(): List<ItemFilm>

    @Insert(entity = ItemFilm::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemFilm)

    @Update
    suspend fun update(item: ItemFilm)
}


@Dao
interface LikedFilmDao {
    @Query("SELECT * FROM isLiked")
    fun getAll(): List<LikedFilms>

    @Insert(entity = LikedFilms::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: LikedFilms)

    @Update
    suspend fun update(item: LikedFilms)

    @Delete
    suspend fun delete(item: LikedFilms)
}

@Dao
interface WantToSeeDao {
    @Query("SELECT * FROM wantToSee")
    fun getAll(): List<WantToSeeFilm>

    @Insert(entity = WantToSeeFilm::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: WantToSeeFilm)

    @Update
    suspend fun update(item: WantToSeeFilm)

    @Delete
    suspend fun delete(item: WantToSeeFilm)
}

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collections")
    fun getAll(): List<CollectionsEntity>

    @Query("SELECT * FROM collections WHERE collectionName  =  :name")
    fun getCollectionList(name: String): CollectionsEntity

    /*  @Query("SELECT * FROM collections WHERE collectionName  LIKE '%' || :collectionNme ||'%'")
      suspend fun getColumnByName(collectionNme:String):String*/

    @Query("SELECT * FROM collections WHERE collectionName  =  :collectionNme ")
    suspend fun getColumnByName(collectionNme: String): CollectionsEntity

    @Insert(entity = CollectionsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    //@Query("INSERT INTO collections values " )
    suspend fun insert(item: CollectionsEntity)

    @Query("UPDATE collections  SET collection = :collection WHERE collectionName = :name ")
    suspend fun updateList(collection: List<Int>, name: String)

    @Delete
    fun delete(item:CollectionsEntity)

}