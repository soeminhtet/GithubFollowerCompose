package com.smh.githubfollowercompose.domain.repository

import androidx.paging.PagingData
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import com.smh.githubfollowercompose.utility.ResponseData
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getFollowers(username: String,searchFollower : String) : Flow<PagingData<FollowerEntity>>

    suspend fun getFollowerDetail(username: String): ResponseData<FollowerDetailApiModel>

    fun getAllFavourites(): Flow<List<FavouriteEntity>>

    suspend fun insertFavourite(favourite : FavouriteEntity)

    suspend fun deleteFavourite(favourite: FavouriteEntity)

    fun checkExist(id : Int) : Flow<Int>
}