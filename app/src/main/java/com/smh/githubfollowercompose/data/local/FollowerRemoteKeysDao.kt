package com.smh.githubfollowercompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smh.githubfollowercompose.domain.model.local.FollowerRemoteKeyEntity

@Dao
interface FollowerRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<FollowerRemoteKeyEntity>)

    @Query("SELECT * FROM FollowerRemoteKeys WHERE id = :id")
    suspend fun remoteKeysRepoId(id : Long): FollowerRemoteKeyEntity?

    @Query("SELECT * FROM FollowerRemoteKeys WHERE username = :username")
    suspend fun firstRemoteKeysUserName(username : String): FollowerRemoteKeyEntity?

    @Query("DELETE FROM FollowerRemoteKeys")
    suspend fun clearRemoteKeys()
}