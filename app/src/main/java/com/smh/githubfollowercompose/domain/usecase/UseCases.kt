package com.smh.githubfollowercompose.domain.usecase

data class UseCases(
    val getFollowersUseCase: GetFollowersUseCase,
    val getFollowerDetailUseCase: GetFollowerDetailUseCase,
    val getFavouritesUseCase: GetFavouritesUseCase,
    val insertFavouriteUseCase: InsertFavouriteUseCase,
    val deleteFavouriteUseCase: DeleteFavouriteUseCase,
    val checkAlreadyFollowed: CheckAlreadyFollowed,
)
