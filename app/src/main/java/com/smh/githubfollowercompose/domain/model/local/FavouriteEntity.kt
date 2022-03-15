package com.smh.githubfollowercompose.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Favourites")
data class FavouriteEntity(
    @PrimaryKey val id : Int,
    val name : String,
    val login : String,
    val imageUrl : String,
    val date : Long = Calendar.getInstance().timeInMillis
)