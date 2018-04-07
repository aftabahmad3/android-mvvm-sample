package com.mobile.sample.main.di

import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.main.UsersViewModel
import dagger.Module
import dagger.Provides

@Module
class UserViewModelModule {

    @Provides
    fun providesUserViewModel(usersRepository: UsersRepository) : UsersViewModel {
        return UsersViewModel(usersRepository)
    }
}