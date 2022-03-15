package com.smh.githubfollowercompose.domain.usecase

import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetFavouritesUseCase(
    private val repository: Repository
) {
    operator fun invoke() : Flow<List<FavouriteEntity>> {
        return repository.getAllFavourites()
    }
}