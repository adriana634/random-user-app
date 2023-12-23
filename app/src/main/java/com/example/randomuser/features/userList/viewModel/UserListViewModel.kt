package com.example.randomuser.features.userList.viewModel

import androidx.lifecycle.ViewModel
import com.example.randomuser.features.userList.model.User

/**
 * ViewModel class for managing the list of users.
 */
class UserListViewModel : ViewModel() {
    private val dummyUsers = listOf(
        User("John Doe", "john.doe@example.com", "https://placehold.co/52x52/png"),
        User("Jane Doe", "jane.doe@example.com", "https://placehold.co/52x52/png"),
        User("Bob Smith", "bob.smith@example.com", "https://placehold.co/52x52/png")
    )

    val users: List<User> get() = dummyUsers
}