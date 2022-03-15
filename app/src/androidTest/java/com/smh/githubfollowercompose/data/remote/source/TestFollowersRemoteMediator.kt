package com.smh.githubfollowercompose.data.remote.source

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.smh.githubfollowercompose.data.local.GithubDatabase
import com.smh.githubfollowercompose.data.remote.api.FakeAndroidApiImpl
import com.smh.githubfollowercompose.data.remote.source.pagingsource.FollowersRemoteMediator
import com.smh.githubfollowercompose.domain.model.local.FollowerEntity
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TestFollowersRemoteMediator {

    private lateinit var api: FakeAndroidApiImpl
    private lateinit var database: GithubDatabase

    @Before
    fun setup() {
        api = FakeAndroidApiImpl()
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GithubDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun cleanup() {
        database.clearAllTables()
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
            val remoteMediator = FollowersRemoteMediator(
                username = "sallen0400",
                api = api,
                database = database
            )
            val pagingState = PagingState<Int, FollowerEntity>(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = 10),
                leadingPlaceholderCount = 0
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            TestCase.assertTrue(result is RemoteMediator.MediatorResult.Success)
            TestCase.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadSuccessAndEndOfPaginationTrueWhenNoMoreData() = runBlocking {
        api.clearList()
        val remoteMediator = FollowersRemoteMediator(
            username = "sallen0400",
            api = api,
            database = database
        )
        val pagingState = PagingState<Int, FollowerEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        TestCase.assertTrue(result is RemoteMediator.MediatorResult.Success)
        TestCase.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        api.addException()
        val remoteMediator = FollowersRemoteMediator(
            username = "sallen0400",
            api = api,
            database = database
        )
        val pagingState = PagingState<Int, FollowerEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        TestCase.assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}