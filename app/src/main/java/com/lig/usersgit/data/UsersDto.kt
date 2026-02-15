package com.lig.usersgit.data

import com.google.gson.annotations.SerializedName

data class UsersDto(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)
