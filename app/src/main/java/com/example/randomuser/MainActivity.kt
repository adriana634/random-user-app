package com.example.randomuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.randomuser.features.userList.ui.UserListScreen
import com.example.randomuser.features.userList.viewModel.UserListViewModel
import com.example.randomuser.features.userList.viewModel.UserListViewModelFactory
import com.example.randomuser.ui.theme.RandomUserTheme

class MainActivity : ComponentActivity() {
    private val viewModelFactory = UserListViewModelFactory()
    private val userListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RandomUserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserListScreen(userListViewModel)
                }
            }
        }
    }
}