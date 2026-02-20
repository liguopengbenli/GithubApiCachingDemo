package com.lig.usersgit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lig.usersgit.presentation.ui.UserDetailScreen
import com.lig.usersgit.presentation.ui.UserScreen
import com.lig.usersgit.ui.theme.UsersgitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UsersgitTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "users",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("users") {
                            UserScreen(
                                onUserClick = { user ->
                                    navController.navigate("user/${user.login}")
                                }
                            )
                        }

                        composable("user/{login}") { backStackEntry ->
                            UserDetailScreen(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                }
            }
        }
    }
}
