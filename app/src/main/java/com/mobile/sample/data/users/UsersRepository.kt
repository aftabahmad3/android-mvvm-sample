package com.mobile.sample.data.users

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import javax.inject.Inject

class UsersRepository @Inject constructor(private val remoteDataSource: UsersRemoteDataSource,
                                          private val localDataSource: UsersLocalDataSource) {

    fun getUsers(): LiveData<List<User>> {
        val data = MutableLiveData<List<User>>()

        localDataSource.getUsers(object: UsersDataSource.LoadUsersCallback {
            override fun onUsersLoaded(users: List<User>) {
                data.value = users
            }

            override fun onDataNotAvailable() {
                fetchUsersFromNetwork(data)
            }
        })

        return data
    }

    private fun fetchUsersFromNetwork(data: MutableLiveData<List<User>>) {
        remoteDataSource.getUsers(object: UsersDataSource.LoadUsersCallback {
            override fun onUsersLoaded(users: List<User>) {
                data.value = users
                localDataSource.insertUsers(users)
            }

            override fun onDataNotAvailable() {
                data.value = emptyList()
            }
        })
    }
}