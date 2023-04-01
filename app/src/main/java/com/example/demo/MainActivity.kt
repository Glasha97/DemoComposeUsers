package com.example.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo.NavigationRoute.USERS_PAGE
import com.example.demo.NavigationRoute.USER_DETAILS
import com.example.demo.ui.user.UserPage
import com.example.demo.ui.users.UsersPage
import com.example.design_system.theme.DemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    contentColor = MaterialTheme.colors.surface,
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "users") {

                        composable(USERS_PAGE.route) {
                            UsersPage(hiltViewModel()) { navController.navigate("userPage/${it.id}") }
                        }
                        composable(USER_DETAILS.route) { UserPage(hiltViewModel()) }
                        kjh
                    }
                }
            }
        }
    }
}
