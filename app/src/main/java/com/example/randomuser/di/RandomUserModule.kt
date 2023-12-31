package com.example.randomuser.di

import com.example.randomuser.features.userList.viewModel.UserListViewModelFactory
import com.example.randomuser.manager.RandomUserManager
import com.example.randomuser.service.RandomUserService
import com.example.randomuser.service.RetrofitClient
import dagger.Module
import dagger.Provides

@Module
class RandomUserModule {

    @Provides
    fun provideRandomUserService(): RandomUserService {
        return createRandomUserService()
    }

    private fun createRandomUserService(): RandomUserService {
        return RetrofitClient.randomUserService
    }

    @Provides
    fun provideRandomUserManager(randomUserService: RandomUserService): RandomUserManager {
        return RandomUserManager(randomUserService)
    }

    @Provides
    fun provideUserListViewModelFactory(randomUserManager: RandomUserManager): UserListViewModelFactory {
        return UserListViewModelFactory(randomUserManager)
    }
}
