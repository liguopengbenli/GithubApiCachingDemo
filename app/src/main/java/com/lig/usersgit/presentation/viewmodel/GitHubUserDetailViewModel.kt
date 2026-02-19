package com.lig.usersgit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lig.usersgit.domain.UserRepository
import com.lig.usersgit.domain.model.User
import com.lig.usersgit.domain.model.UserDetail
import com.lig.usersgit.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubUserDetailViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _user = MutableStateFlow<UiState<UserDetail>>(UiState.Loading)
    val user: StateFlow<UiState<UserDetail>> = _user.asStateFlow()


    fun fetchUsers(username: String) {
        viewModelScope.launch {
            Log.d("ViewModel", "Fetching users")
            _user.value = UiState.Loading
            userRepository.getUserDetail(username).collect { result->
                _user.value = result.fold(
                    onSuccess = { UiState.Success(it) },
                    onFailure = { UiState.Error(it, it.message ?: "Unknown error") }
                )
            }
        }
    }

}