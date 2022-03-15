package com.smh.githubfollowercompose.data.remote.source.pagingsource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.smh.githubfollowercompose.data.local.GithubDatabase
import com.smh.githubfollowercompose.data.remote.api.GitHubApi
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.model.local.FollowerRemoteKeyEntity
import com.smh.githubfollowercompose.domain.model.local.toFollowerModel
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FollowersRemoteMediator(
    private val username : String,
    private val api: GitHubApi,
    private val database: GithubDatabase
) : RemoteMediator<Int, FollowerEntity>() {

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = database.followerKeysDao().firstRemoteKeysUserName(username)?.lastUpdated ?: 0L
        val cacheTimeout = 1440

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60
        return if (diffInMinutes.toInt() <= cacheTimeout) {
            Log.i("REFRESH","SKIP_INITIAL_REFRESH")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.i("REFRESH","LAUNCH_INITIAL_REFRESH")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FollowerEntity>
    ): MediatorResult {
        val page : Int = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val previousKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                previousKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = api.getFollowers(username = username, page = page)
            val endOfPaginationReached = apiResponse.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.followerKeysDao().clearRemoteKeys()
                    database.followersDao().clearRepos()
                }

                val previousKey = if (page == 1) null else page-1
                val nextKey = if (endOfPaginationReached) null else page+1

                val keys = apiResponse.map {
                    FollowerRemoteKeyEntity(id = it.id, prevKey = previousKey, nextKey = nextKey, username = username)
                }
                database.followerKeysDao().insertAll(keys)
                database.followersDao().insertAll(apiResponse.map { it.toFollowerModel(searchName = username) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }
        catch (e : IOException) {
            return MediatorResult.Error(e)
        }
        catch (e : HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FollowerEntity>): FollowerRemoteKeyEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull{ it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.followerKeysDao().remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FollowerEntity>): FollowerRemoteKeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                database.followerKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, FollowerEntity>): FollowerRemoteKeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.followerKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }
}