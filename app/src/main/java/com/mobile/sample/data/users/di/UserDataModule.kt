package com.mobile.sample.data.users.di

import com.mobile.sample.data.database.AppDatabase
import com.mobile.sample.data.network.NetworkManager
import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class UserDataModule {

    @Provides
    fun providesUserRepository(remoteDataSource: UsersRemoteDataSource, localDataSource: UsersLocalDataSource): UsersRepository {
        return UsersRepository(remoteDataSource, localDataSource)
    }

    @Provides
    fun providesUsersRemoteDataSource(networkManager: NetworkManager): UsersRemoteDataSource {
        return UsersRemoteDataSource(networkManager)
    }

    @Provides
    fun providesUsersLocalDataSource(appDatabase: AppDatabase): UsersLocalDataSource {
        return UsersLocalDataSource(appDatabase)
    }
}