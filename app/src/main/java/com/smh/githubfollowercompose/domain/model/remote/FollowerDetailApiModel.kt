package com.smh.githubfollowercompose.domain.model.remote

import com.google.gson.annotations.SerializedName

data class FollowerDetailApiModel(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("public_repos")
    val publicRepos: Int,
)

val dummyDetail = FollowerDetailApiModel(
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