package com.smh.githubfollowercompose.domain.repository

import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAllFavourites(): Flow<List<FavouriteEntity>>

    suspend fun insertFavourite(favourite : FavouriteEntity)

    suspend fun deleteFavourite(favourite: FavouriteEntity)

    fun checkExist(id : Int) : Flow<Int>
}