package com.lig.usersgit.domain

import com.lig.usersgit.domain.model.User
import com.lig.usersgit.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow


interface UserRepository {

    // don't need suspend cold flow create instantly heavy operation is in collect
    fun getUsers(): Flow<Result<List<User>>>
    fun getUserDetail(username: String): Flow<Result<UserDetail>>
}

