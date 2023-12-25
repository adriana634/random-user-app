package com.example.randomuser.features.userList.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randomuser.features.userList.model.User
import com.example.randomuser.manager.RandomUserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * ViewModel class for managing the list of users.
 */
class UserListViewModel(private val randomUserManager: RandomUserManager) : ViewModel() {

    companion object {
        // TAG for logging purposes.
        private val TAG = UserListViewModel::class.java.simpleName
        private const val INITIAL_USER_COUNT = 20
    }

    private val _users = MutableLiveData<List<User>>()
    private var _navigateToUserDetails by mutableStateOf<User?>(null)

    val users: LiveData<List<User>> get() = _users
    val navigateToUserDetails: User? get() = _navigateToUserDetails

    suspend fun loadUsersAsync() {
        try {
            val response = withContext(Dispatchers.IO) {
                randomUserManager.getRandomUsers(INITIAL_USER_COUNT)
            }

            if (response.isSuccessful) {
                _users.value = response.body()
            } else {
                Log.e(TAG, "API error: ${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error", e)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error", e)
        }
    }

    fun onUserItemClick(user: User) {
        Log.d(TAG, "User clicked: ${user.email}")
        _navigateToUserDetails = user
    }

    fun onUserDetailsScreenNavigated() {
        Log.d(TAG, "Navigating to UserDetailsScreen")
        _navigateToUserDetails = null
    }
}