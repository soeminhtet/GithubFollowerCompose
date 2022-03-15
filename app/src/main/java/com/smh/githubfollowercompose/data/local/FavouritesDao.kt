package com.smh.githubfollowercompose.data.local

import androidx.room.*
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao{

    @Query("SELECT * FROM Favourites Order by date DESC")
    fun getAll(): Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favourite : FavouriteEntity)

    @Delete
    suspend fun delete(favourite: FavouriteEntity)

    @Query("Select Count(*) from Favourites where id = :id")
    fun checkExist(id : Int) : Flow<Int>
}