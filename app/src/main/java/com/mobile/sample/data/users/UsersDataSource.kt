package com.mobile.sample.data.users

typealias OnUsersLoaded = (List<User>) -> Unit
typealias OnUserLoaded = (User) -> Unit
typealias OnDataNotAvailable = () -> Unit

interface UsersDataSource {

    fun getUsers(onUsersLoaded: OnUsersLoaded, onDataNotAvailable: OnDataNotAvailable)
}