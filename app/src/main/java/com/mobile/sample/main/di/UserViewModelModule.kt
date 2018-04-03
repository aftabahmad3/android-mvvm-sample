package com.mobile.sample.main.di

import com.mobile.sample.data.network.users.UsersRepository
import com.mobile.sample.main.UsersViewModel
import dagger.Module

@Module
class UserViewModelModule {

    fun providesUserViewModel(usersRepository: UsersRepository) : UsersViewModel {
        return UsersViewModel(usersRepository)
    }
}