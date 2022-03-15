package com.smh.githubfollowercompose.domain.usecase

import com.smh.githubfollowercompose.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckAlreadyFollowed (
    private val repository: Repository
) {
    operator fun invoke(id : Int) : Flow<Boolean> {
        return repository.checkExist(id).map { it > 0 }
    }
}