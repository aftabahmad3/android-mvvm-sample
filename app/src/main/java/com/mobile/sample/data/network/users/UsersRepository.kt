package com.mobile.sample.data.network.users

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import javax.inject.Inject

class UsersRepository @Inject constructor(private val remoteDataSource: UsersDataSource) {

    fun getUsers(): LiveData<List<User>> {
        val data = MutableLiveData<List<User>>()

        remoteDataSource.getUsers(object: UsersDataSource.LoadUsersCallback {
            override fun onUsersLoaded(users: List<User>) {
                data.value = users
            }

            override fun onDataNotAvailable() {
                data.value = emptyList()
            }
        })
        return data
    }
}