package com.example.randomuser.features.userList.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
        private const val FIRST_PAGE = 1
        private const val PAGE_INCREMENT = 1
        private const val MAX_PAGES_IN_MEMORY = 3
    }

    private var _usersLoaded = false
    private var _isLoading = false
    private var _currentPage = FIRST_PAGE

    // Maintain a mapping between user emails and their corresponding page indices
    private val _userPageIndexMap = mutableMapOf<String, Int>()

    // Maintain a set of loaded page indices
    private val _loadedPages = mutableSetOf<Int>()

    private var _allUserModels = mutableListOf<User>()
    private val _usersViewModels = MutableLiveData<List<UserListItemViewModel>>()
    private var _navigateToUserDetails by mutableStateOf<String?>(null)

    private val clearPagesLock = Any()

    val navigateToUserDetails: String? get() = _navigateToUserDetails
    val isLoading: Boolean get() = _isLoading

    /**
     * Asynchronously loads users from the network.
     *
     * @param page The page number to load.
     */
    private suspend fun loadUsersAsync(page: Int) {
        if (_isLoading) {
            return
        }

        Log.i(TAG, "Loading users, page: $page")

        _isLoading = true

        // If loading the first page, reset the state
        if (page == FIRST_PAGE) {
            _usersLoaded = false
        }

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    randomUserManager.getRandomUsers(INITIAL_USER_COUNT, page)
                }

                withContext(Dispatchers.Main) {
                    when (result) {
                        is Result.Success -> {
                            val newUsers = result.data

                            _allUserModels.addAll(newUsers)

                            newUsers.forEach { user ->
                                _userPageIndexMap[user.email] = page
                            }

                            _usersViewModels.value = _allUserModels.map { user ->
                                UserListItemViewModel(user,this@UserListViewModel)
                            }

                            // If loading the first page, mark as loaded
                            if (page == FIRST_PAGE) {
                                _usersLoaded = true
                            }
                            _currentPage = page

                            // Update the set of loaded pages
                            _loadedPages.add(page)
                        }
                        is Result.Error -> {
                            Log.e(TAG, "API error: ${result.message}")
                        }
                    }
                }
            } catch (error: Throwable) {
                Log.e(TAG, "Unexpected error", error)
            } finally {
                _isLoading = false
            }
        }
    }

    // Clear existing users when reaching the maximum number of pages in memory
    private fun clearPagesFromMemoryIfNeeded() {
        synchronized(clearPagesLock) {
            // Check if the number of distinct page indices exceeds the maximum allowed
            if (_loadedPages.count() > MAX_PAGES_IN_MEMORY) {
                Log.i(TAG, "Clearing pages from memory")

                val distinctPageIndices = _userPageIndexMap.values.toSet()

                // Determine the oldest page index
                val oldestPageIndex = distinctPageIndices.minOrNull() ?: return

                // Update the set of loaded pages before removing users
                _loadedPages.remove(oldestPageIndex)

                // Clear the view models associated with the oldest page index(s)
                _usersViewModels.value = _usersViewModels.value?.filterNot { viewModel ->
                    val email = viewModel.email
                    val userPageIndex = _userPageIndexMap[email]
                    if (userPageIndex == oldestPageIndex) {
                        // Remove the entry from the userPageIndexMap when removing the user
                        _userPageIndexMap.remove(email)
                    }
                    userPageIndex == oldestPageIndex
                } ?: emptyList()

                // Clear the users associated with the oldest page index(s)
                _allUserModels = _allUserModels.filterNot { user ->
                    val userPageIndex = _userPageIndexMap[user.email]
                    if (userPageIndex == oldestPageIndex) {
                        // Remove the entry from the userPageIndexMap when removing the user
                        _userPageIndexMap.remove(user.email)
                    }
                    userPageIndex == oldestPageIndex
                }.toMutableList()
            }
        }
    }

    suspend fun loadUsersAsync() {
        loadUsersAsync(FIRST_PAGE)
        loadNextUsersAsync()
        loadNextUsersAsync()
        loadNextUsersAsync()
    }

    suspend fun loadNextUsersAsync() {
        if (_isLoading) {
            return
        }

        loadUsersAsync(_currentPage + PAGE_INCREMENT)
        clearPagesFromMemoryIfNeeded()
    }

    suspend fun loadPreviousUsersAsync(page: Int) {
        // If the current page is the first page or it's already loaded, return early.
        if (_currentPage == FIRST_PAGE || _loadedPages.contains(page - PAGE_INCREMENT)) {
            return
        }

        loadUsersAsync(page)
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
            combine(_usersViewModels.asFlow(), MutableStateFlow(query)) { users, searchQuery ->
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
