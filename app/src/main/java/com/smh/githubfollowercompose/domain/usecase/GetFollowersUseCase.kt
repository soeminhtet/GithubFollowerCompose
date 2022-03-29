package com.smh.githubfollowercompose.domain.usecase

import androidx.paging.PagingData
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

class GetFollowersUseCase(
    private val repository: Repository
) {
    operator fun invoke(name : String,searchFollower : String): Flow<PagingData<FollowerEntity>> {
        return repository.getFollowers(name, searchFollower = searchFollower)
    }
}