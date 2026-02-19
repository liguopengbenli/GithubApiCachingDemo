package com.lig.usersgit.data.remote.model

import com.google.gson.annotations.SerializedName

//The GitHub API returns the same UserDto shape for both endpoints. The list endpoint just returns fewer fields (nulls/defaults for the detail fields).
data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("name") val name: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("blog") val blog: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("public_repos") val publicRepos: Int,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int,
)