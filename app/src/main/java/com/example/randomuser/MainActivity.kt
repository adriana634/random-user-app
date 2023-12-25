package com.example.randomuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.randomuser.features.userDetails.ui.UserDetailsScreen
import com.example.randomuser.features.userList.ui.UserListScreen
import com.example.randomuser.features.userList.viewModel.UserListViewModel
import com.example.randomuser.features.userList.viewModel.UserListViewModelFactory
import com.example.randomuser.ui.theme.RandomUserTheme

class MainActivity : ComponentActivity() {
    private val viewModelFactory = UserListViewModelFactory()
    private val userListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RandomUserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "userListScreen"
        ) {
            composable("userListScreen") {
                UserListScreen(navController, userListViewModel)
            }

            composable("userDetailsScreen/{userEmail}") { backStackEntry ->
                val userEmail = backStackEntry.arguments?.getString("userEmail")
                val user = userEmail?.let { userListViewModel.findUserModelByEmail(it) }
                user?.let {
                    UserDetailsScreen(user = it)
                }
            }
        }
    }
}