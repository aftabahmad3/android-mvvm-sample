package com.mobile.sample.data.users.local

import android.util.Log
import com.mobile.sample.Mockable
import com.mobile.sample.data.users.*
import com.mobile.sample.database.AppDatabase
import com.mobile.sample.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

@Mockable
class UsersLocalDataSource @Inject constructor(
        private val database: AppDatabase,
        private val coroutineContextProvider: CoroutineContextProvider) : UsersDataSource {

    private val asyncJobs: MutableList<Job> = mutableListOf()

    override fun getUsers(onUsersLoaded: OnUsersLoaded, onDataNotAvailable: OnDataNotAvailable) {
        val job = launch(coroutineContextProvider.Main) {
            try {
                val job = async(coroutineContextProvider.IO) {
                    database.userDao().getUsers()
                }
                asyncJobs.add(job)
                val users = job.await()
                if (users.isEmpty()) onDataNotAvailable() else onUsersLoaded(users)
            } catch (exception: Exception) {
                onDataNotAvailable()
            }
        }
        asyncJobs.add(job)
    }

    /* TODO: Should this tell repo that items were not inserted? */
    fun insertUsers(users: List<User>, onDataNotAvailable: OnDataNotAvailable) {
        val job = async(coroutineContextProvider.IO) {
            try {
                database.userDao().insertUsers(users)
            } catch (exception: Exception) {
                onDataNotAvailable()
            }
        }
        asyncJobs.add(job)
    }

    fun getUser(userId: Int, onUserLoaded: OnUserLoaded, onDataNotAvailable: OnDataNotAvailable) {
        val job = launch(coroutineContextProvider.Main) {
            try {
                val job = async(coroutineContextProvider.IO) {
                    database.userDao().getUser(userId)
                }
                asyncJobs.add(job)
                val user = job.await()
                onUserLoaded(user)
            } catch (exception: Exception) {
                Log.d("exception:", "exception: $exception")
                onDataNotAvailable()
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