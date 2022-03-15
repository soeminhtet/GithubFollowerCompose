package com.smh.githubfollowercompose.domain.usecase

import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.repository.Repository

class InsertFavouriteUseCase (
    private val repository: Repository
) {
    suspend operator fun invoke(favourite : FavouriteEntity) {
        repository.insertFavourite(favourite)
    }
}