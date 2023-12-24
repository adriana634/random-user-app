package com.example.randomuser.features.userList.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomuser.features.userList.viewModel.UserListViewModel
import com.example.randomuser.ui.theme.RandomUserTheme

/**
 * Composable function for the screen displaying a list of users.
 *
 * @param userListViewModel The ViewModel providing user data.
 */
@Composable
fun UserListScreen(userListViewModel: UserListViewModel = viewModel()) {
    val users by userListViewModel.users.observeAsState(initial = emptyList())

    LaunchedEffect(userListViewModel) {
        userListViewModel.loadUsersAsync()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        UserList(users)
    }
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    RandomUserTheme {
        UserListScreen()
    }
}