package com.mobile.sample.data.network.users

interface UsersDataSource {

    interface LoadUsersCallback {
        fun onUsersLoaded(users: List<User>)
        fun onDataNotAvailable()
    }

    fun getUsers(callback: LoadUsersCallback)
}