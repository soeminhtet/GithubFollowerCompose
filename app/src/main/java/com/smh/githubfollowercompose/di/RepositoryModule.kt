package com.smh.githubfollowercompose.di

import com.smh.githubfollowercompose.data.repository.RepositoryImpl
import com.smh.githubfollowercompose.domain.repository.LocalDataSource
import com.smh.githubfollowercompose.domain.repository.RemoteDataSource
import com.smh.githubfollowercompose.domain.repository.Repository
import com.smh.githubfollowercompose.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(local : LocalDataSource,remote : RemoteDataSource) : Repository {
        return RepositoryImpl(local = local, remote = remote)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            getFollowersUseCase = GetFollowersUseCase(repository = repository),
            getFollowerDetailUseCase = GetFollowerDetailUseCase(repository = repository),
            getFavouritesUseCase = GetFavouritesUseCase(repository = repository),
            insertFavouriteUseCase = InsertFavouriteUseCase(repository = repository),
            deleteFavouriteUseCase = DeleteFavouriteUseCase(repository = repository),
            checkAlreadyFollowed = CheckAlreadyFollowed(repository = repository)
        )
    }

}