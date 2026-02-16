package com.lig.usersgit.presentation

import com.lig.usersgit.domain.model.User

// sealed class Exhaustive Compile-Time Checks
// only use wrapper in presentation layer
sealed class UiState<out T>{
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    // Throwable is the base class for all errors in Kotlin using in flow
    data class Error(val exception: Throwable, val message: String) : UiState<Nothing>()
}