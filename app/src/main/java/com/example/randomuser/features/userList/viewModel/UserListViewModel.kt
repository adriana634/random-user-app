package com.example.randomuser.features.userList.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.randomuser.manager.RandomUserManager
import com.example.randomuser.model.User
import com.example.randomuser.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel class for managing the list of users.
 */
class UserListViewModel(private val randomUserManager: RandomUserManager) : ViewModel(),
    OnUserItemClickListener {

    companion object {
        // TAG for logging purposes.
        private val TAG = UserListViewModel::class.java.simpleName
        private const val INITIAL_USER_COUNT = 100
    }

    private var usersLoaded = false

    private val _users = MutableLiveData<List<UserListItemViewModel>>()
    private var _navigateToUserDetails by mutableStateOf<String?>(null)

    private val _allUserModels = mutableListOf<User>()

    val users: LiveData<List<UserListItemViewModel>> get() = _users
    val navigateToUserDetails: String? get() = _navigateToUserDetails

    /**
     * Asynchronously loads users from the network if not already loaded.
     * If the users are already loaded, this method does nothing.
     */
    suspend fun loadUsersAsync() {
        if (usersLoaded) {
            return
        }

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    randomUserManager.getRandomUsers(INITIAL_USER_COUNT)
                }

                withContext(Dispatchers.Main) {
                    when (result) {
                        is Result.Success -> {
                            val users = result.data

                            _allUserModels.addAll(users)

                            _users.value = _allUserModels.map { user ->
                                UserListItemViewModel(user, this@UserListViewModel)
                            }
                            usersLoaded = true
                        }
                        is Result.Error -> {
                            Log.e(TAG, "API error: ${result.message}")
                        }
                    }
                }
            } catch (error: Throwable) {
                Log.e(TAG, "Unexpected error", error)
            }
        }
    }

    override fun onUserItemClick(email: String) {
        Log.d(TAG, "User clicked: $email")
        _navigateToUserDetails = email
    }

    fun onUserDetailsScreenNavigated() {
        Log.d(TAG, "Navigating to UserDetailsScreen")
        _navigateToUserDetails = null
    }

    fun findUserModelByEmail(email: String): User? {
        return _allUserModels.find { it.email == email }
    }

    fun getUsersWithQuery(query: String): MutableLiveData<List<UserListItemViewModel>> {
        val filteredUsers = MutableLiveData<List<UserListItemViewModel>>()

        viewModelScope.launch {
            combine(_users.asFlow(), MutableStateFlow(query)) { users, searchQuery ->
                if (searchQuery.isBlank()) {
                    users
                } else {
                    users.filter { user ->
                        user.name.contains(searchQuery, ignoreCase = true) ||
                                user.email.contains(searchQuery, ignoreCase = true)
                    }
                }
            }.collect { filteredUsers.value = it }
        }

        return filteredUsers
    }
}
