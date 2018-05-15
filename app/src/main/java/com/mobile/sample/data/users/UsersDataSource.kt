package com.mobile.sample.data.users

interface UsersDataSource {

    suspend fun getUsers() : List<User>
}