package com.lig.usersgit.presentation.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lig.usersgit.domain.model.User
import com.lig.usersgit.presentation.GitHubUsersViewModel
import com.lig.usersgit.presentation.UiState

@Composable
fun UserScreen(viewModel: GitHubUsersViewModel) {
    /*
    * collectAsState(): Keeps the "pipe" open. If your Flow is fetching data from a database or a high-frequency sensor, it continues to work and drain battery while the user can't even see the UI.
      collectAsStateWithLifecycle(): Automatically stops collecting when the app goes into the background and re-starts when the app comes back to the foreground.
    * */
    val state by viewModel.users.collectAsStateWithLifecycle()

    //using val uiState = state inside the when block is done to ensure atomicity and consistency.
    //normally it handle in delegate already
    when (val uiState = state) {
        UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> {
            UserList(uiState.data)
        }

        is UiState.Error -> {
            val message = uiState.message
            Text(text = message)
        }
    }
}

@Composable
fun UserList(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            Text(text = user.login)
        }
    }
}
