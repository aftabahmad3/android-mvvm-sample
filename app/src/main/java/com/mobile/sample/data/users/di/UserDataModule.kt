package com.mobile.sample.data.users.di

import com.mobile.sample.data.network.NetworkManager
import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class UserDataModule {

    @Provides
    fun providesUsersRemoteDataSource(networkManager: NetworkManager) : UsersRemoteDataSource {
        return UsersRemoteDataSource(networkManager)
    }

    @Provides
    fun providesUserRepository(remoteDataSource: UsersRemoteDataSource) : UsersRepository {
        return UsersRepository(remoteDataSource)
    }
}