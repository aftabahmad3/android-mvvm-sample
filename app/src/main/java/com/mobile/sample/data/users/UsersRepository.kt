package com.mobile.sample.data.users

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mobile.sample.Mockable
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import com.mobile.sample.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

@Mockable
class UsersRepository @Inject constructor(
        private val contextProvider: CoroutineContextProvider,
        private val remoteDataSource: UsersRemoteDataSource,
        private val localDataSource: UsersLocalDataSource) {

    private val asyncJobs: MutableList<Job> = mutableListOf()


    fun getUsers(): LiveData<List<User>> {
        val data = MutableLiveData<List<User>>()

        val job = launch(contextProvider.Main) {
            try {
                val users = localDataSource.getUsers()
                if (users.isEmpty()) fetchUserFromNetwork(data) else data.value = users
            } catch (exception: Exception) {
                fetchUserFromNetwork(data)
            }
        }

        asyncJobs.add(job)
        return data
    }

    fun getUser(userId: Int): LiveData<User> {
        val data = MutableLiveData<User>()

        val job = launch(contextProvider.Main) {
            try {
                val user = localDataSource.getUser(userId)
                data.value = user
            } catch (exception: Exception) {

            }
        }

        asyncJobs.add(job)
        return data
    }

    private suspend fun fetchUserFromNetwork(data: MutableLiveData<List<User>>) {
        val users = remoteDataSource.getUsers()
        localDataSource.insertUsers(users)
        data.value = users
    }

    fun cleanUpJobs() {
        asyncJobs.forEach {
            it.cancel()
        }
    }
}