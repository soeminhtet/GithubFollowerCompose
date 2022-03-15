package com.smh.githubfollowercompose.di

import com.smh.githubfollowercompose.data.local.GithubDatabase
import com.smh.githubfollowercompose.data.remote.api.GitHubApi
import com.smh.githubfollowercompose.data.remote.source.datasource.RemoteDataSourceImpl
import com.smh.githubfollowercompose.domain.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideAPI(): GitHubApi {
        return Retrofit.Builder().baseUrl("https://api.github.com/users/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: GitHubApi,database : GithubDatabase) : RemoteDataSource {
        return RemoteDataSourceImpl(api = api, database = database)
    }
}