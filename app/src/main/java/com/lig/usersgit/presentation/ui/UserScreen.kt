package com.lig.usersgit.presentation.ui

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.lig.usersgit.domain.model.User
import com.lig.usersgit.presentation.viewmodel.GitHubUsersViewModel
import com.lig.usersgit.presentation.UiState
import com.lig.usersgit.presentation.UserEvent
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    viewModel: GitHubUsersViewModel = hiltViewModel(),
    onUserClick: (User) -> Unit,
) {
    /*
    * collectAsState(): Keeps the "pipe" open. If your Flow is fetching data from a database or a high-frequency sensor, it continues to work and drain battery while the user can't even see the UI.
      collectAsStateWithLifecycle(): Automatically stops collecting when the app goes into the background and re-starts when the app comes back to the foreground.
    * */
    val state by viewModel.users.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()


    //using val uiState = state inside the when block is done to ensure atomicity and consistency.
    //normally it handle in delegate already
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        onRefresh = {
            viewModel.onEvent(UserEvent.Refresh)
        },
        isRefreshing = isRefreshing,
        state = rememberPullToRefreshState(),
    ) {
        when (val uiState = state) {
            UiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            is UiState.Success -> {
                UserList(uiState.data, onUserClick = onUserClick)
            }

            is UiState.Error -> {
                val message = uiState.message
                Text(
                    text = message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserList(users: List<User>, onUserClick: (User) -> Unit) {
    //LazyColumn only "composes" and lays out the items currently visible on the screen

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // add key for items to avoid recomposition
        items(users, key = { it.id }) { user ->
            UserItem(
                user,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = {
                    onUserClick(user)
                }
            )
        }
    }
}


@Composable
fun UserItem(user: User, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.clickable {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically
    )
    {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatarUrl)
                .memoryCacheKey(user.avatarUrl)
                .diskCacheKey(user.avatarUrl)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .crossfade(true)
                .build(),
            contentDescription = "User profile image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_menu_gallery),
            error = painterResource(id = R.drawable.ic_menu_gallery)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = user.login,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
