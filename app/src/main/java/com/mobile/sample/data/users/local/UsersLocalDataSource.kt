package com.mobile.sample.data.users.local

import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersDataSource
import com.mobile.sample.database.AppDatabase
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(private val database: AppDatabase) : UsersDataSource {

    override suspend fun getUsers() = database.userDao().getUsers()

    suspend fun insertUsers(users: List<User>) = database.userDao().insertUsers(users)

    suspend fun getUser(id: Int) = database.userDao().getUser(id)
}