package com.example.randomuser.di

import com.example.randomuser.features.userList.viewModel.UserListViewModelFactory
import com.example.randomuser.manager.RandomUserManager
import com.example.randomuser.navigation.NavControllerNavigator
import com.example.randomuser.navigation.Navigator
import com.example.randomuser.service.RandomUserService
import com.example.randomuser.service.RetrofitClient
import dagger.Module
import dagger.Provides

@Module
class RandomUserModule {

    private fun createRandomUserService(): RandomUserService {
        return RetrofitClient.randomUserService
    }

    @Provides
    fun provideRandomUserService(): RandomUserService {
        return createRandomUserService()
    }

    @Provides
    fun provideRandomUserManager(randomUserService: RandomUserService): RandomUserManager {
        return RandomUserManager(randomUserService)
    }

    @Provides
    fun provideNavigator(): Navigator {
        return NavControllerNavigator()
    }

    @Provides
    fun provideUserListViewModelFactory(randomUserManager: RandomUserManager): UserListViewModelFactory {
        return UserListViewModelFactory(randomUserManager)
    }
}
