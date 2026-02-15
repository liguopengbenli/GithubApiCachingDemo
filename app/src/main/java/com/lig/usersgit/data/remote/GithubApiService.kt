package com.lig.usersgit.data.remote

import com.lig.usersgit.data.UsersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
//    @GET("search/users")
//    suspend fun searchUsers(
//        @Query("q") query: String,
//    ): List<UsersDto>

    // one shot operation not stream without flow
    @GET("users")
    suspend fun getUsers(): List<UsersDto>
}