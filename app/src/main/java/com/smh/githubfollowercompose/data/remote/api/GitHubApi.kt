package com.smh.githubfollowercompose.data.remote.api

import com.smh.githubfollowercompose.domain.model.remote.FollowerApiModel
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import retrofit2.Response
import retrofit2.http.*

interface GitHubApi {

    @Headers("User-Agent: request")
    @GET("{username}/followers?per_page=50")
    suspend fun getFollowers(
        @Path("username") username : String,
        @Query("page") page : Int
    ) : List<FollowerApiModel>

    @Headers("User-Agent: request")
    @GET("{username}")
    suspend fun getFollowerDetail(
        @Path("username") username: String
    ) : Response<FollowerDetailApiModel>
}