package com.smh.githubfollowercompose.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity

@Dao
interface FollowersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<FollowerEntity>)

    @Query("SELECT * FROM Followers WHERE searchName LIKE :searchName")
    fun reposByName(searchName : String): PagingSource<Int, FollowerEntity>

    @Query("DELETE FROM Followers")
    suspend fun clearRepos() : Void
}