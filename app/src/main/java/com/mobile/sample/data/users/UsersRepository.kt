package com.mobile.sample.data.users

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mobile.sample.Mockable
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import javax.inject.Inject

@Mockable
class UsersRepository @Inject constructor(private val remoteDataSource: UsersRemoteDataSource,
                                          private val localDataSource: UsersLocalDataSource) {

    fun getUsers(): LiveData<List<User>> {
        val data = MutableLiveData<List<User>>()

        localDataSource.getUsers(
                onUsersLoaded = { data.value = it },
                onDataNotAvailable = { fetchUsersFromNetwork(data) }
        )

        return data
    }

    fun getUser(userId: Int): LiveData<User> {
        val data = MutableLiveData<User>()

        localDataSource.getUser(userId,
                { data.value = it },
                {
                    // TODO: Handle Error
                }
        )

        return data
    }

    private fun fetchUsersFromNetwork(data: MutableLiveData<List<User>>) {
        remoteDataSource.getUsers(
                onUsersLoaded = {
                    data.value = it
                    localDataSource.insertUsers(it, {
                        // TODO: Send message to ViewModel -> View to say users not saved?
                    })
                },
                onDataNotAvailable = { data.value = emptyList() }
        )
    }

    fun cleanUpJobs() {
        localDataSource.cleanupJobs()
        remoteDataSource.cleanupJobs()
    }
}