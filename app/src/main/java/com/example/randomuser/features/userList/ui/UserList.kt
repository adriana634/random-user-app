package com.example.randomuser.features.userList.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomuser.model.User
import com.example.randomuser.ui.theme.RandomUserTheme
import java.util.Date

/**
 * Composable function to display a list of users.
 *
 * @param users The list of users to be displayed.
 */
@Composable
fun UserList(users: List<User>, onItemClick: ((User) -> Unit)? = null) {
    LazyColumn {
        items(users) { user ->
            UserListItem(user, onItemClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    RandomUserTheme {
        val sampleUsers = getSampleUserList()
        UserList(users = sampleUsers)
    }
}

private fun getSampleUserList(): List<User> {
    return listOf(
        User("John Doe", "john.doe@example.com", "male", "https://placehold.co/52x52/png", Date(), "123456789"),
        User("Jane Doe", "jane.doe@example.com", "female", "https://placehold.co/52x52/png", Date(), "123456789"),
        User("Bob Smith", "bob.smith@example.com", "male", "https://placehold.co/52x52/png", Date(), "123456789")
    )
}