package com.smh.githubfollowercompose.domain.repository

import androidx.paging.PagingData
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import com.smh.githubfollowercompose.utility.ResponseData
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getFollowers(username: String,searchFollower : String) : Flow<PagingData<FollowerEntity>>

    suspend fun getFollowerDetail(username: String): ResponseData<FollowerDetailApiModel>
}