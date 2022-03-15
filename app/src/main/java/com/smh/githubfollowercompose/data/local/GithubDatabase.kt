package com.smh.githubfollowercompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import com.smh.githubfollowercompose.domain.model.local.FollowerRemoteKeyEntity

@Database(
    entities = [FavouriteEntity::class,FollowerEntity::class,FollowerRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun favouritesDao() : FavouritesDao
    abstract fun followersDao() : FollowersDao
    abstract fun followerKeysDao() : FollowerRemoteKeysDao
}