package com.lig.usersgit.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// You should generally use one single UserEntity, even if the data comes from two different API endpoints (List vs. Detail).
// Since the List API provides less info than the Detail API, the "extra" fields in your Entity must be nullable.
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val avatarUrl: String,
    val login: String,
    val lastUpdated: Long ,// Store as primitive for Room
    val bio: String? = null,
    val location: String? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val name: String? = null,
)