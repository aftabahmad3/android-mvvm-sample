package com.mobile.sample.data.users

import android.arch.lifecycle.MutableLiveData
import com.mobile.sample.Mockable
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import com.mobile.sample.utils.CoroutineContextProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Mockable
class UsersRepository @Inject constructor(
        private val contextProvider: CoroutineContextProvider,
        private val remoteDataSource: UsersRemoteDataSource,
        private val localDataSource: UsersLocalDataSource) {

    suspend fun getUsers(data: MutableLiveData<Result<List<User>>>) = coroutineScope {
        try {
            var users = localDataSource.getUsers()
            if (users.isEmpty()) {
                fetchFromNetwork()
                users = localDataSource.getUsers()
                mainContext { data.value = Result.success(users) }
            } else {
                mainContext { data.value = Result.success(users) }
            }
        } catch (error: Throwable) {
            mainContext { data.value = Result.failure(error) }
        }
    }

    suspend fun fetchFromNetwork() {
        remoteDataSource.getUsers().apply {
            localDataSource.insertUsers(this).await()
        }
    }

    private suspend fun mainContext(block: () -> Unit) {
        withContext(contextProvider.Main) {
            block()
        }
    }
}