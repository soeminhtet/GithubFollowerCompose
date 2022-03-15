package com.smh.githubfollowercompose.data.remote.api

import com.smh.githubfollowercompose.TestConstants
import com.smh.githubfollowercompose.domain.model.remote.FollowerApiModel
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FakeApiImpl : FakeApi(),GitHubApi {

    private var service : GitHubApi

    init {
        val url = mockWebServer.url("/")
        service = Retrofit.Builder()
            .baseUrl(url)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

    override suspend fun getFollowers(username: String, page: Int): List<FollowerApiModel> {
        enqueue200("Followers.json")
        return service.getFollowers(username, page)
    }

    override suspend fun getFollowerDetail(username: String): Response<FollowerDetailApiModel> {
        if(username == TestConstants.name) {
            enqueue200("FollowerDetail.json")
        }
        else {
            enqueue200("FollowerDetailEmpty.json")
        }
        return service.getFollowerDetail(username)
    }
}