package com.lig.usersgit.presentation.state

import com.lig.usersgit.domain.model.User

data class UsersUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: String? = null
)