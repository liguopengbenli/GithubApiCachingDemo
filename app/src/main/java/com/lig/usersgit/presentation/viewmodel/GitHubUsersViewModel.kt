package com.lig.usersgit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lig.usersgit.domain.UserRepository
import com.lig.usersgit.presentation.state.UserEvent
import com.lig.usersgit.presentation.state.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubUsersViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()
    //asStateFlow() creates a read-only wrapper
    //that physically strips away the "setter" capabilities,

    private val events = MutableSharedFlow<UserEvent>(
        replay = 0, // one shot
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        // collect in compose with launcheffect is better because of snackbar etc
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    UserEvent.Refresh -> fetchUsers(isRefresh = true)
                    UserEvent.ClearError -> _uiState.update { state ->
                        state.copy(error = null)
                    }
                }
            }
        }
        fetchUsers(isRefresh = false)
    }

    fun onEvent(event: UserEvent) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    private var fetchJob: Job? = null
    private fun fetchUsers(isRefresh: Boolean) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (isRefresh) {
                _uiState.update { state ->
                    state.copy(isRefreshing = true)
                }
            } else {
                _uiState.update { state ->
                    state.copy(isLoading = true)
                }
            }
            Log.d("ViewModel", "Fetching users forceRefresh: $isRefresh")
            userRepository.getUsers(forceRefresh = isRefresh).collect { result ->
                result.fold(
                    onSuccess = { users ->
                        _uiState.update { state ->
                            state.copy(
                                users = users,
                                isLoading = false,
                                isRefreshing = false
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update { state ->
                            state.copy(
                                error = error.message,
                                isLoading = false,
                                isRefreshing = false
                            )
                        }
                    }
                )
            }
        }
    }

}