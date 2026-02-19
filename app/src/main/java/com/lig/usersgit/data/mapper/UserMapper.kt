package com.lig.usersgit.data.mapper

import com.lig.usersgit.data.local.model.UserEntity
import com.lig.usersgit.data.remote.model.UserDto
import com.lig.usersgit.domain.model.User
import com.lig.usersgit.domain.model.UserDetail
import java.sql.Timestamp

fun UserDto.toUser(): User =
    User(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        bio = bio,
        timestamp = Timestamp(
            System.currentTimeMillis()
        )
    )

fun UserDto.toUserEntity(): UserEntity = UserEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    name = name,
    bio = bio,
    location = location,
    followers = followers,
    following = following,
    lastUpdated = System.currentTimeMillis()
)

fun UserDto.toUserDetail(): UserDetail = UserDetail(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    name = name,
    bio = bio,
    location = location,
    followers = followers,
    following = following,
)

fun UserEntity.toUser(): User {
    return User(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        bio = bio,
        timestamp = Timestamp(lastUpdated)
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        bio = bio,
        lastUpdated = timestamp.time
    )
}

fun UserEntity.toUserDetail(): UserDetail {
    return UserDetail(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        name = name,
        bio = bio,
        location = location,
        followers = followers ?: 0,
        following = following ?: 0,
    )
}

fun UserDetail.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        bio = bio,
        location = location,
        followers = followers,
        following = following,
        name = name,
        lastUpdated = System.currentTimeMillis()
    )
}

