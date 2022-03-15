package com.smh.githubfollowercompose.di

import android.content.Context
import androidx.room.Room
import com.smh.githubfollowercompose.data.local.FavouritesDao
import com.smh.githubfollowercompose.data.local.GithubDatabase
import com.smh.githubfollowercompose.data.local.LocalDataSourceImpl
import com.smh.githubfollowercompose.domain.repository.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context) : GithubDatabase {
        return Room.databaseBuilder(
            context,
            GithubDatabase::class.java, "Github_Db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouritesDao(database: GithubDatabase) : FavouritesDao {
        return database.favouritesDao()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(favouritesDao: FavouritesDao) : LocalDataSource {
        return LocalDataSourceImpl(favouritesDao = favouritesDao)
    }
}