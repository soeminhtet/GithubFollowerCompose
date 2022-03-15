package com.smh.githubfollowercompose.data.remote.api

import com.smh.githubfollowercompose.domain.model.remote.FollowerApiModel
import com.smh.githubfollowercompose.domain.model.remote.FollowerDetailApiModel
import retrofit2.Response
import java.io.IOException

class FakeAndroidApiImpl : GitHubApi {

    private var exception = false
    private var list = listOf(
        FollowerApiModel(
            login = "SAllen0400",
            avatarUrl = "https://avatars.githubusercontent.com/u/10645516?v=4",
            htmlUrl = "https://github.com/SAllen0400",
            id = 10645516,
            eventsUrl = "",
            followersUrl = "",
            followingUrl = "",
            gistsUrl = "",
            nodeId = "",
            organizationsUrl = "",
            receivedEventsUrl = "",
            reposUrl = "",
            siteAdmin = false,
            starredUrl = "",
            subscriptionsUrl = "",
            type = "",
            url = ""
        )
    )

    private val detail = FollowerDetailApiModel(
        login = "SAllen0400",
        avatarUrl = "https://avatars.githubusercontent.com/u/10645516?v=4",
        name = "Sean Allen",
        location = "Shwebo",
        bio = "Mobile Application Developer",
        createdAt = "2015-01-22T01:46:25Z",
        followers = 0,
        following = 100,
        htmlUrl = "https://github.com/SAllen0400",
        id = 10645516,
        publicGists = 14,
        publicRepos = 4
    )

    fun clearList() {
        list = emptyList()
    }

    fun addException() {
        exception = true
    }

    override suspend fun getFollowers(username: String, page: Int): List<FollowerApiModel> {
        if (exception) throw IOException()
        return list
    }

    override suspend fun getFollowerDetail(username: String): Response<FollowerDetailApiModel> {
        return Response.success(detail)
    }
}