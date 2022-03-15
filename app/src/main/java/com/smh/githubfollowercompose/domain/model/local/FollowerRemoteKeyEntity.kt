package com.smh.githubfollowercompose.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FollowerRemoteKeys")
data class FollowerRemoteKeyEntity(
    @PrimaryKey
    val id: Long,
    val prevKey: Int?,
    val nextKey: Int?,
    val username : String,
    val lastUpdated : Long = System.currentTimeMillis()
)