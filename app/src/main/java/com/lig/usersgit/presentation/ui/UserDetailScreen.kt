package com.lig.usersgit.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.lig.usersgit.domain.model.UserDetail
import com.lig.usersgit.presentation.viewmodel.GitHubUserDetailViewModel

@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: GitHubUserDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.error != null -> Text(text = state.error!!)
            state.isLoading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            state.user != null -> UserDetailCard(
                state.user!!,
                modifier = Modifier.align(Alignment.Center)
            )

        }
    }

}

@Composable
fun UserDetailCard(user: UserDetail, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                .size(240.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
            error = painterResource(id = android.R.drawable.ic_menu_gallery)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = user.login,
            style = MaterialTheme.typography.bodyLarge
        )
        user.bio?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user.followers.toString(),
            style = MaterialTheme.typography.bodyLarge
        )

    }
}