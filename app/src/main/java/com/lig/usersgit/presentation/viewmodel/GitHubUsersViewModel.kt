package com.lig.usersgit.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lig.usersgit.domain.UserRepository
import com.lig.usersgit.domain.model.User
import com.lig.usersgit.presentation.UiState
import com.lig.usersgit.presentation.UserEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubUsersViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _users = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    //asStateFlow() creates a read-only wrapper
    //that physically strips away the "setter" capabilities,
    val users: StateFlow<UiState<List<User>>> = _users.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()


   private val events = MutableSharedFlow<UserEvent>()

    init {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    UserEvent.Refresh ->  fetchUsers(isRefresh = true)

                }
            }
        }
        fetchUsers(isRefresh = false)
    }

    fun onEvent(event: UserEvent){
        viewModelScope.launch {
            events.emit(event)
        }
    }


    private fun fetchUsers(isRefresh: Boolean) {
        viewModelScope.launch {
            if (isRefresh) {
                _isRefreshing.value = true
            } else {
                _users.value = UiState.Loading  // only blank screen on initial load
            }
            Log.d("ViewModel", "Fetching users")
            userRepository.getUsers().collect { result->
                _users.value = result.fold(
                    onSuccess = { UiState.Success(it) },
                    onFailure = { UiState.Error(it, it.message ?: "Unknown error") }
                )
                _isRefreshing.value = false
            }
        }
    }

}