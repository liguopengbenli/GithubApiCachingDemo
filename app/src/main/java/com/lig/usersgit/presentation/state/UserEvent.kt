package com.lig.usersgit.presentation.state

sealed class UserEvent {
    object Refresh: UserEvent()
    object ClearError : UserEvent()
}