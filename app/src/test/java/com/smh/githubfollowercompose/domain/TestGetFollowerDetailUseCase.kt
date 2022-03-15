package com.smh.githubfollowercompose.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.smh.githubfollowercompose.TestConstants
import com.smh.githubfollowercompose.data.repository.FakeRepositoryImpl
import com.smh.githubfollowercompose.domain.usecase.GetFollowerDetailUseCase
import com.smh.githubfollowercompose.utility.ResponseData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class TestGetFollowerDetailUseCase {
    private lateinit var repository: FakeRepositoryImpl

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = FakeRepositoryImpl()
    }

    @Test
    fun `username has info, expect username info`() {
        runTest {
            val response = GetFollowerDetailUseCase(repository = repository).invoke(username = TestConstants.name)
            assert(response is ResponseData.Success)
            val data = (response as ResponseData.Success).data
            assert(data.login == "SAllen0400")
        }
    }

    @Test
    fun `username has not info, expect username info to null`() {
        runTest {
            val response = GetFollowerDetailUseCase(repository = repository).invoke(username = "soeminhtet")
            assert(response is ResponseData.Success)
            val data = (response as ResponseData.Success).data
            assert(data.login.isNullOrEmpty())
        }
    }
}