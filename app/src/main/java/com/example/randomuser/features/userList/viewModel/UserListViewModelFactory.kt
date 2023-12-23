package com.example.randomuser.features.userList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Factory class for creating instances of the [UserListViewModel].
 */
class UserListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}