package com.mobile.sample.data.users

interface UsersDataSource {

    interface LoadUsersCallback {
        fun onUsersLoaded(users: List<User>)
        fun onDataNotAvailable()
    }

    fun getUsers(callback: LoadUsersCallback)
}