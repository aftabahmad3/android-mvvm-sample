package com.mobile.sample.data.network.users.di

import com.mobile.sample.data.network.users.UsersRepository
import com.mobile.sample.data.network.users.remote.UsersRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class UserRepositoryModule {

    @Provides
    fun providesUserRepository(remoteDataSource: UsersRemoteDataSource) : UsersRepository {
        return UsersRepository(remoteDataSource)
    }
}