package com.lig.usersgit.presentation

sealed class UserEvent {
    object Refresh: UserEvent()
}