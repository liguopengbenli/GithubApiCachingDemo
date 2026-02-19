package com.lig.usersgit.domain.model

data class UserDetail(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val bio: String?,
    val location: String?,
    val followers: Int,
    val following: Int,
)