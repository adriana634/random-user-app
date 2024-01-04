package com.example.randomuser.di

import com.example.randomuser.MainActivity
import com.example.randomuser.features.userList.viewModel.UserListViewModelFactory
import com.example.randomuser.manager.RandomUserManager
import com.example.randomuser.navigation.NavControllerNavigator
import dagger.Component

@Component(modules = [RandomUserModule::class])
interface RandomUserComponent {

    fun inject(randomUserManager: RandomUserManager)
    fun inject(navigator: NavControllerNavigator)
    fun inject(userListViewModelFactory: UserListViewModelFactory)
    fun inject(mainActivity: MainActivity)
}
