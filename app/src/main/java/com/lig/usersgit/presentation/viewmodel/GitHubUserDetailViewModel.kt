package com.lig.usersgit.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lig.usersgit.domain.UserRepository
import com.lig.usersgit.presentation.state.UserDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GitHubUserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val username: String = checkNotNull(savedStateHandle["login"])
    val uiState = userRepository.getUserDetail(username).map { result ->
        result.fold(
            onSuccess = { UserDetailUiState(user = it, isLoading = false) },
            onFailure = { UserDetailUiState(error = it.message, isLoading = false) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserDetailUiState(isLoading = true)
    )
}
