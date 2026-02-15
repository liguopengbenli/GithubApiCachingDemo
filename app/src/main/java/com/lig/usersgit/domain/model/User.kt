package com.lig.usersgit.domain.model

import java.sql.Timestamp

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val timestamp: Timestamp
)
