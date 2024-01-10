package com.example.randomuser.features.userList.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomuser.R
import com.example.randomuser.features.userList.viewModel.UserListViewModel
import com.example.randomuser.navigation.NavControllerNavigator
import com.example.randomuser.navigation.Navigator
import com.example.randomuser.ui.theme.RandomUserTheme
import kotlinx.coroutines.launch

/**
 * Composable function for the screen displaying a list of users.
 *
 * @param userListViewModel The ViewModel providing user data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(navigator: Navigator, userListViewModel: UserListViewModel = viewModel()) {
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

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Localized description"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text("Contacts")
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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

            UserList(
                navigator,
                users,
                lazyListState,
                onNextPage = { page ->
                    coroutineScope.launch {
                        userListViewModel.loadNextUsersAsync(page)
                    }
                },
                onPreviousPage = { page ->
                    coroutineScope.launch {
                        userListViewModel.loadPreviousUsersAsync(page)
                    }
                },
                isLoading = isLoading
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    RandomUserTheme {
        UserListScreen(NavControllerNavigator())
    }
}