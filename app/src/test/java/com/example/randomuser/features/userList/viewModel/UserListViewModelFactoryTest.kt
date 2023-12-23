package com.example.randomuser.features.userList.viewModel

import androidx.lifecycle.ViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the [UserListViewModelFactory] class.
 */
class UserListViewModelFactoryTest {

    private lateinit var userListViewModelFactory: UserListViewModelFactory

    @Before
    fun setUp() {
        userListViewModelFactory = UserListViewModelFactory()
    }

    /**
     * Test creating an instance of [UserListViewModel] using the factory.
     * Verifies that the factory successfully creates a [UserListViewModel] instance.
     */
    @Test
    fun `test create UserListViewModel`() {

        val viewModelClass = UserListViewModel::class.java

        val createdViewModel = userListViewModelFactory.create(viewModelClass)

        Assert.assertTrue(viewModelClass.isAssignableFrom(createdViewModel.javaClass))
    }

    /**
     * Test creating an instance of an unknown ViewModel class using the factory.
     * Verifies that the factory throws an [IllegalArgumentException] when attempting
     * to create an instance of an unknown ViewModel class.
     */
    @Test(expected = IllegalArgumentException::class)
    fun `test create unknown ViewModel`() {
        val unknownViewModelClass = UnknownViewModel::class.java

        userListViewModelFactory.create(unknownViewModelClass)
    }

    private open class UnknownViewModel : ViewModel()
}