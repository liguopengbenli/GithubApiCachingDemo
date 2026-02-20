package com.lig.usersgit.data.remote

import com.lig.usersgit.data.remote.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
//    @GET("search/users")
//    suspend fun searchUsers(
//        @Query("q") query: String,
//    ): List<UsersDto>

    // one shot operation not stream without flow
    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDto
}

