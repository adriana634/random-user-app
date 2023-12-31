package com.example.randomuser.features.userList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randomuser.manager.RandomUserManager
import javax.inject.Inject

/**
 * Factory class for creating instances of the [UserListViewModel].
 */
class UserListViewModelFactory @Inject constructor(private val randomUserManager: RandomUserManager) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(randomUserManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
