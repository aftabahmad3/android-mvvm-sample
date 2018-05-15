package com.mobile.sample.data.users.local

import com.mobile.sample.Mockable
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersDataSource
import com.mobile.sample.database.AppDatabase
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

@Mockable
class UsersLocalDataSource @Inject constructor(private val database: AppDatabase) : UsersDataSource {

    override suspend fun getUsers() = database.userDao().getUsersAsync()

    suspend fun insertUsers(users: List<User>) = async { database.userDao().insertUsers(users) }

    suspend fun getUser(id: Int) = database.userDao().getUserAsync(id)

}