package com.smh.githubfollowercompose.data.repository

import androidx.paging.PagingData
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import com.smh.githubfollowercompose.domain.repository.LocalDataSource
import com.smh.githubfollowercompose.domain.repository.RemoteDataSource
import com.smh.githubfollowercompose.domain.repository.Repository
import com.smh.githubfollowercompose.utility.ResponseData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val local : LocalDataSource
) : Repository {
    override fun getFollowers(username: String,searchFollower : String) : Flow<PagingData<FollowerEntity>> =  remote.getFollowers(username = username, searchFollower = searchFollower)

    override suspend fun getFollowerDetail(username: String): ResponseData<FollowerDetailApiModel> = remote.getFollowerDetail(username = username)

    override fun getAllFavourites(): Flow<List<FavouriteEntity>> = local.getAllFavourites()

    override suspend fun insertFavourite(favourite : FavouriteEntity) = local.insertFavourite(favourite)

    override suspend fun deleteFavourite(favourite: FavouriteEntity) = local.deleteFavourite(favourite)

    override fun checkExist(id : Int) : Flow<Int> = local.checkExist(id)
}