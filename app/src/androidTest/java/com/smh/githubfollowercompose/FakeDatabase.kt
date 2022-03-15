package com.smh.githubfollowercompose

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.smh.githubfollowercompose.data.local.GithubDatabase

object FakeDatabase {
    val database = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        GithubDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}