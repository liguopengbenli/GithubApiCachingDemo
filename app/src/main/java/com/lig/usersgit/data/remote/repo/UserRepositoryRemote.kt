package com.lig.usersgit.data.remote.repo

import com.lig.usersgit.data.UsersDto
import com.lig.usersgit.data.remote.GithubApiService
import com.lig.usersgit.domain.UserRepository
import com.lig.usersgit.domain.model.User
import com.lig.usersgit.presentation.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.sql.Timestamp
import javax.inject.Inject

class UserRepositoryRemote @Inject constructor(private val apiService: GithubApiService) :
    UserRepository {
    override suspend fun getUsers(): Flow<Result<List<User>>> = flow {
        val users = apiService.getUsers().map { it.toUser() }
        emit(Result.success(users))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}

private fun UsersDto.toUser(): User =
    User(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        timestamp = Timestamp(
            System.currentTimeMillis()
        )
    )



