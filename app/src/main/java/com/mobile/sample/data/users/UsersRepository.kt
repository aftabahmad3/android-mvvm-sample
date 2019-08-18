package com.mobile.sample.data.users

import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UserRemoteModel
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import com.mobile.sample.utils.Failure
import com.mobile.sample.utils.Result
import com.mobile.sample.utils.Success
import javax.inject.Inject

class UsersRepository @Inject constructor(
        private val remoteDataSource: UsersRemoteDataSource,
        private val localDataSource: UsersLocalDataSource) {

    suspend fun getUsers(): Result<List<User>> {
        return try {
            var users : List<User> = localDataSource.getUsers()
            if (users.isEmpty()) {
                users = fetchFromNetwork()
            }

            Success(users)
        } catch (error: Throwable) {
            Failure(error)
        }
    }

    suspend fun getUser(userId: Int): Result<User> {
        return try {
            Success(localDataSource.getUser(userId))
        } catch (error: Throwable) {
            Failure(error)
        }
    }

    private suspend fun fetchFromNetwork(): List<UserRemoteModel> {
        return remoteDataSource.getUsers().apply {
            localDataSource.insertUsers(this)
        }
    }
}