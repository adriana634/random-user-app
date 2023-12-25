package com.example.randomuser.features.userList.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.randomuser.features.userList.viewModel.UserListViewModel
import com.example.randomuser.ui.theme.RandomUserTheme

/**
 * Composable function for the screen displaying a list of users.
 *
 * @param userListViewModel The ViewModel providing user data.
 */
@Composable
fun UserListScreen(navController: NavController, userListViewModel: UserListViewModel = viewModel()) {
    val users by userListViewModel.users.observeAsState(initial = emptyList())

    LaunchedEffect(userListViewModel) {
        userListViewModel.loadUsersAsync()
    }

    val navigateToUserDetails by rememberUpdatedState(userListViewModel.navigateToUserDetails)
    navigateToUserDetails?.let { user ->
        navController.navigate("userDetailsScreen/${user.email}")
        userListViewModel.onUserDetailsScreenNavigated()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        UserList(users, onItemClick = { userListViewModel.onUserItemClick(it) })
    }
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    RandomUserTheme {
        val mockNavController = rememberNavController()
        UserListScreen(mockNavController)
    }
}