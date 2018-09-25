package com.mobile.sample.data.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobile.sample.Mockable
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UserRemoteModel
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import com.mobile.sample.utils.CoroutineContextProvider
import com.mobile.sample.utils.Failure
import com.mobile.sample.utils.Result
import com.mobile.sample.utils.Success
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.coroutineScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

@Mockable
class UsersRepository @Inject constructor(
        private val contextProvider: CoroutineContextProvider,
        private val remoteDataSource: UsersRemoteDataSource,
        private val localDataSource: UsersLocalDataSource) {

    fun getUsers(scope: CoroutineScope): LiveData<Result<List<User>>> {
        val data = MutableLiveData<Result<List<User>>>()
        scope.launch {
            try {
                var users = backgroundContext { localDataSource.getUsers() }
                if (users.isEmpty()) {
                    users = backgroundContext { fetchFromNetwork() }
                    data.value = Success(users)
                } else {
                    data.value = Success(users)
                }
            } catch (error: Throwable) {
                data.value = Failure(error)
            }
        }

        return data
    }

    suspend fun fetchFromNetwork(): List<UserRemoteModel> {
        return remoteDataSource.getUsers().apply {
            localDataSource.insertUsers(this)
        }
    }

    private suspend fun backgroundContext(block: suspend () -> List<User>) = coroutineScope {
        withContext(contextProvider.IO) {
            block()
        }
    }
}