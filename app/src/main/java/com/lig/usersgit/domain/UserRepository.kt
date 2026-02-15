package com.lig.usersgit.domain

import com.lig.usersgit.domain.model.User
import com.lig.usersgit.presentation.UiState
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    suspend fun getUsers(): Flow<Result<List<User>>>
}