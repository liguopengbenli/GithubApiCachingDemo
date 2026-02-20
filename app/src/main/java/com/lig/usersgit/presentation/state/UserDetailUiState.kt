package com.lig.usersgit.presentation.state

import com.lig.usersgit.domain.model.UserDetail

data class UserDetailUiState(
    val user: UserDetail? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
