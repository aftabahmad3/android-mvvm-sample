package com.mobile.sample.data.users.dagger

import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import com.mobile.sample.database.AppDatabase
import com.mobile.sample.network.NetworkManager
import com.mobile.sample.utils.CoroutineContextProvider
import dagger.Module
import dagger.Provides

@Module
class UserDataModule {

    @Provides
    fun providesUserRepository(contextProvider: CoroutineContextProvider,
                               remoteDataSource: UsersRemoteDataSource,
                               localDataSource: UsersLocalDataSource): UsersRepository {
        return UsersRepository(contextProvider, remoteDataSource, localDataSource)
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