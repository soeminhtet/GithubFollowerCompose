package com.smh.githubfollowercompose.data.remote.source.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smh.githubfollowercompose.data.remote.api.GitHubApi
import com.smh.githubfollowercompose.domain.model.remote.FollowerApiModel
import retrofit2.HttpException
import java.io.IOException

class FollowersPagingSource(
    private val api: GitHubApi,
    private val username: String
) : PagingSource<Int, FollowerApiModel>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, FollowerApiModel> {
        val position = params.key ?: 1

        return try {
            val repo = api.getFollowers(username = username,page = position)
            val nextKey = if (repo.isEmpty()) null else { position + (params.loadSize / 50) }
            LoadResult.Page(
                data = repo,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        }
        catch (exception: IOException) {
            Log.i("LoadError IOException",exception.message ?: "IOException")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i("LoadError HttpException",exception.message ?: "HttpException")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FollowerApiModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}