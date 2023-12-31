package com.example.randomuser.di

import com.example.randomuser.MainActivity
import com.example.randomuser.features.userList.viewModel.UserListViewModelFactory
import com.example.randomuser.manager.RandomUserManager
import dagger.Component

@Component(modules = [RandomUserModule::class])
interface RandomUserComponent {

    fun inject(randomUserManager: RandomUserManager)
    fun inject(userListViewModelFactory: UserListViewModelFactory)
    fun inject(mainActivity: MainActivity)
}
