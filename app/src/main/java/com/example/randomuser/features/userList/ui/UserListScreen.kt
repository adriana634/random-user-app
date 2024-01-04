package com.example.randomuser.features.userList.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.randomuser.R
import com.example.randomuser.features.userList.viewModel.UserListViewModel
import com.example.randomuser.ui.theme.RandomUserTheme
import kotlinx.coroutines.launch

/**
 * Composable function for the screen displaying a list of users.
 *
 * @param userListViewModel The ViewModel providing user data.
 */
@Composable
fun UserListScreen(navController: NavController, userListViewModel: UserListViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf("") }
    val users by userListViewModel.getUsersWithQuery(searchQuery).observeAsState(initial = emptyList())
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(userListViewModel.isLoading) }

    LaunchedEffect(userListViewModel) {
        userListViewModel.loadUsersAsync()
    }

    LaunchedEffect(userListViewModel.isLoading) {
        isLoading = userListViewModel.isLoading
    }

    val navigateToUserDetails by rememberUpdatedState(userListViewModel.navigateToUserDetails)
    navigateToUserDetails?.let { email ->
        navController.navigate("userDetailsScreen/${email}")
        userListViewModel.onUserDetailsScreenNavigated()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                },
                placeholder = { Text(stringResource(R.string.search_by_name_or_email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            UserList(users, lazyListState,
                onNextPage = {
                    coroutineScope.launch {
                        userListViewModel.loadNextUsersAsync()
                    }
            },  onPreviousPage = { page ->
                    coroutineScope.launch {
                        userListViewModel.loadPreviousUsersAsync(page)
                    }
            }, isLoading = isLoading)
        }
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