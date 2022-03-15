package com.smh.githubfollowercompose.data.remote.source.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smh.githubfollowercompose.data.local.GithubDatabase
import com.smh.githubfollowercompose.data.remote.api.GitHubApi
import com.smh.githubfollowercompose.data.remote.source.pagingsource.FollowersRemoteMediator
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import com.smh.githubfollowercompose.domain.repository.RemoteDataSource
import com.smh.githubfollowercompose.utility.ResponseData
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val api : GitHubApi,
    private val database: GithubDatabase,
) : RemoteDataSource {
    @OptIn(ExperimentalPagingApi::class)
    override fun getFollowers(username: String,searchFollower : String) : Flow<PagingData<FollowerEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false,
                initialLoadSize = 50,
            ),
            remoteMediator = FollowersRemoteMediator(
                api = api,
                database = database,
                username = username
            ),
            pagingSourceFactory = { database.followersDao().reposByName(searchName = username) }
        ).flow
    }

    override suspend fun getFollowerDetail(username: String): ResponseData<FollowerDetailApiModel> {
        try {
            val data = api.getFollowerDetail(username)
            when (data.code()) {
                200 -> {
                    data.body()?.let {
                        return ResponseData.Success(data = it)
                    }
                    return ResponseData.Failed(200,"This user has any data to show.")
                }
                else -> return ResponseData.Failed(status = data.code(), message = data.message())
            }
        }
        catch (e : Exception) {
            return ResponseData.Exception(e)
        }
    }
}