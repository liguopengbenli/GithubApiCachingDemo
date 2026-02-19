package com.lig.usersgit.data.repo

import com.lig.usersgit.data.local.room.UserDao
import com.lig.usersgit.data.mapper.toUser
import com.lig.usersgit.data.mapper.toUserDetail
import com.lig.usersgit.data.mapper.toUserEntity
import com.lig.usersgit.data.remote.GithubApiService
import com.lig.usersgit.domain.UserRepository
import com.lig.usersgit.domain.model.User
import com.lig.usersgit.domain.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//The definition of the Repository Pattern in Android is to coordinate data from multiple sources.
// If you have two repositories (Remote and Local), your ViewModel has to write logic like: "Call remote, get result, save to local, then read from local." This is bad architecture because it leaks business logic into the UI layer.
class UserRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService,
    private val userDao: UserDao
) : UserRepository {

    override fun getUsers(): Flow<Result<List<User>>> = flow {
        var cachedUsers = emptyList<User>()
        // cache first
        userDao.getAllUsers().firstOrNull()?.let { cached ->
            if (cached.isNotEmpty()){
                cachedUsers = cached.map { it.toUser() }
                emit (Result.success(cachedUsers))
            }
        }
        
        try {
            // fetch fresh data from server, always convert to entity first instead of domain (lost data)
            val usersEntity = apiService.getUsers().map { it.toUserEntity() }
            userDao.clearAll() // Optional: clear old data if you want a full refresh
            userDao.insertUsers(usersEntity)
            emit(Result.success(usersEntity.map { it.toUser() }))
        } catch (e: Exception) {
            // If we have cached data, just log error or ignore it so UI keeps showing data
            if (cachedUsers.isEmpty()) {
                emit(Result.failure(e))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserDetail(username: String): Flow<Result<UserDetail>> = flow {
        var cachedUser: UserDetail? = null
        // cache first
        userDao.getUser(username).firstOrNull()?.let { cached->
             cachedUser = cached.toUserDetail()
             emit(Result.success(cachedUser!!))
        }

        try {
            // fetch fresh data from server
            val userEntity = apiService.getUserDetail(username).toUserEntity()
            userDao.insertUser(userEntity)
            emit(Result.success(userEntity.toUserDetail()))
        } catch (e: Exception) {
            if (cachedUser == null) {
                emit(Result.failure(e))
            }
        }
    }.flowOn(Dispatchers.IO)

}
