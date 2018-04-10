package com.mobile.sample.data.users.local

import com.mobile.sample.database.AppDatabase
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersDataSource
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(private val database: AppDatabase) : UsersDataSource {

    private val asyncJobs: MutableList<Job> = mutableListOf()

    override fun getUsers(callback: UsersDataSource.LoadUsersCallback) {
        val job = launch(UI) {
            try {
                val job = async(CommonPool) {
                    database.userDao().getUsers()
                }
                asyncJobs.add(job)
                val users = job.await()
                if (users.isEmpty()) callback.onDataNotAvailable() else callback.onUsersLoaded(users)
            } catch (exception: Exception) {
                callback.onDataNotAvailable()
            }
        }
        asyncJobs.add(job)
    }

    fun insertUsers(users: List<User>) {
        val job = async(CommonPool) {
            try {
                database.userDao().insertUsers(users)
            } catch (exception: Exception) {

            }
        }
        asyncJobs.add(job)
    }

    fun cleanupJobs() {
        asyncJobs.forEach {
            it.cancel()
        }
    }
}