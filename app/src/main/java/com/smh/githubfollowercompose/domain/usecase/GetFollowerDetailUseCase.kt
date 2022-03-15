package com.smh.githubfollowercompose.domain.usecase

import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import com.smh.githubfollowercompose.domain.repository.Repository
import com.smh.githubfollowercompose.utility.ResponseData

class GetFollowerDetailUseCase (
    private val repository: Repository
) {
    suspend operator fun invoke(username : String) : ResponseData<FollowerDetailApiModel> {
        return repository.getFollowerDetail(username)
    }
}