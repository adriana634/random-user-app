package com.example.randomuser.features.userList.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomuser.features.userList.viewModel.UserListItemViewModel
import com.example.randomuser.model.User
import com.example.randomuser.navigation.NavControllerNavigator
import com.example.randomuser.navigation.Navigator
import com.example.randomuser.ui.theme.RandomUserTheme
import java.util.Date

/**
 * Composable function to display a list of users.
 *
 * @param users The list of users to be displayed.
 * @param lazyListState The state of the LazyColumn.
 * @param onNextPage Function to be called when the user scrolls to the bottom of the list.
 * @param onPreviousPage Function to be called when the user scrolls to the top of the list.
 * @param isLoading Boolean indicating whether data is currently being loaded.
 * @param preFetchPages Number of pages to pre-fetch.
 * @param initialPlaceholderItems Number of initial placeholder items to be displayed.
 */
@Composable
fun UserList(
    navigator: Navigator,
    users: List<UserListItemViewModel>,
    lazyListState: LazyListState,
    onNextPage: () -> Unit,
    onPreviousPage: (page: Int) -> Unit,
    isLoading: Boolean,
    preFetchPages: Int = 4,
    initialPlaceholderItems: Int = 8
) {
    var previousPage by remember { mutableIntStateOf(0) }

    // Detect scroll position
    LaunchedEffect(lazyListState.firstVisibleItemIndex, lazyListState.layoutInfo.totalItemsCount) {
        val visibleItemIndex = lazyListState.firstVisibleItemIndex
        val totalItemCount = lazyListState.layoutInfo.totalItemsCount

        val itemsPerPage = lazyListState.layoutInfo.visibleItemsInfo.size
        if (itemsPerPage != 0) {
            val currentPage = (visibleItemIndex / itemsPerPage) + 1

            Log.d("UserList", "Current Page: $currentPage")

            if (currentPage < previousPage) {
                onPreviousPage(currentPage)
            } else if (visibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size >= totalItemCount - preFetchPages) {
                onNextPage()
            }

            previousPage = currentPage
        }
    }

    LazyColumn(state = lazyListState) {
        items(users) { user ->
            UserListItem(navigator, user)
        }

        if (isLoading) {
            items(initialPlaceholderItems) {
                PlaceholderItem()
            }
        }
    }
}

@Composable
fun PlaceholderItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder Image
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1F))
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Placeholder Text
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3F))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3F))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    val lazyListState = rememberLazyListState()
    RandomUserTheme {
        val sampleUsers = getSampleUserList()
        UserList(NavControllerNavigator(), users = sampleUsers, lazyListState, onNextPage = {}, onPreviousPage = {}, false)
    }
}

private fun getSampleUserList(): List<UserListItemViewModel> {
    return listOf(
        UserListItemViewModel(User("John Doe", "john.doe@example.com", "male", "https://placehold.co/52x52/png", "https://placehold.co/52x52/png", Date(), "123456789")),
        UserListItemViewModel(User("Jane Doe", "jane.doe@example.com", "female", "https://placehold.co/52x52/png", "https://placehold.co/52x52/png", Date(), "123456789")),
        UserListItemViewModel(User("Bob Smith", "bob.smith@example.com", "male", "https://placehold.co/52x52/png", "https://placehold.co/52x52/png", Date(), "123456789"))
    )
}