package com.smh.githubfollowercompose.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.smh.githubfollowercompose.data.remote.source.FakeRemoteDataSourceImpl
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import com.smh.githubfollowercompose.domain.repository.Repository
import com.smh.githubfollowercompose.utility.ResponseData
import kotlinx.coroutines.flow.Flow

class FakeRepositoryImpl : Repository{
    private val remote = FakeRemoteDataSourceImpl()

    override fun getFollowers(
        username: String,
        searchFollower: String
    ): Flow<PagingData<FollowerEntity>> {
        return remote.getFollowers(username = username,searchFollower = "")
    }

    override suspend fun getFollowerDetail(username: String): ResponseData<FollowerDetailApiModel> {
        return remote.getFollowerDetail(username = username)
    }

    override fun getAllFavourites(): Flow<List<FavouriteEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavourite(favourite: FavouriteEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavourite(favourite: FavouriteEntity) {
        TODO("Not yet implemented")
    }

    override fun checkExist(id: Int): Flow<Int> {
        TODO("Not yet implemented")
    }
}