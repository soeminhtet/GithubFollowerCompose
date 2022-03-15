package com.smh.githubfollowercompose.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smh.githubfollowercompose.domain.model.remote.FollowerApiModel

@Entity(tableName = "Followers")
data class FollowerEntity(
    @PrimaryKey val id : Long,
    val name : String,
    val imageUrl : String,
    val searchName : String,
)

fun FollowerApiModel.toFollowerModel(searchName: String) : FollowerEntity {
    return FollowerEntity(
        id = this.id,
        name = this.login,
        imageUrl = this.avatarUrl,
        searchName = searchName
    )
}
