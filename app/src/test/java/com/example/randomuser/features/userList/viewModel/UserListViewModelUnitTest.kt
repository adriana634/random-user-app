package com.example.randomuser.features.userList.viewModel

import com.example.randomuser.features.userList.model.User
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the [UserListViewModel] class.
 */
class UserListViewModelUnitTest {

    private val dummyUsers = listOf(
        User("John Doe", "john.doe@example.com", "https://placehold.co/52x52/png"),
        User("Jane Doe", "jane.doe@example.com", "https://placehold.co/52x52/png"),
        User("Bob Smith", "bob.smith@example.com", "https://placehold.co/52x52/png")
    )

    private lateinit var userListViewModel: UserListViewModel

    @Before
    fun setUp() {
        userListViewModel = UserListViewModel()
    }

    /**
     * Test the initial state of the [UserListViewModel].
     * Verifies that the ViewModel provides the correct initial state with dummy users.
     */
    @Test
    fun `test initial state`() {
        assertEquals(userListViewModel.users, dummyUsers)
    }
}