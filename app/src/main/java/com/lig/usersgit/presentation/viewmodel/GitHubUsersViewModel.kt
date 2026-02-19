package com.lig.usersgit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lig.usersgit.domain.UserRepository
import com.lig.usersgit.domain.model.User
import com.lig.usersgit.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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


    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            Log.d("ViewModel", "Fetching users")
            _users.value = UiState.Loading
            userRepository.getUsers().collect { result->
                _users.value = result.fold(
                    onSuccess = { UiState.Success(it) },
                    onFailure = { UiState.Error(it, it.message ?: "Unknown error") }
                )
            }
        }
    }

}