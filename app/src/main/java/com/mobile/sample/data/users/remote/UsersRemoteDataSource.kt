package com.mobile.sample.data.users.remote

import com.mobile.sample.network.NetworkManager
import com.mobile.sample.data.users.UsersDataSource
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class UsersRemoteDataSource
@Inject constructor(private val networkManager: NetworkManager) : UsersDataSource {

    private val asyncJobs: MutableList<Job> = mutableListOf()

    override fun getUsers(callback: UsersDataSource.LoadUsersCallback) {
        val jobs = launch(UI) {
            try {
                val users = networkManager.getUsers().await()
                if (users.isEmpty()) callback.onDataNotAvailable() else callback.onUsersLoaded(users)
            } catch (exception: Exception) {
                callback.onDataNotAvailable()
            }
        }

        asyncJobs.add(jobs)
    }

    fun cleanAsyncJobs() {
        asyncJobs.forEach {
            it.cancel()
        }
    }
}