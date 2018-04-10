package com.mobile.sample.data.users.remote

import com.mobile.sample.data.users.OnDataNotAvailable
import com.mobile.sample.data.users.OnUsersLoaded
import com.mobile.sample.data.users.UsersDataSource
import com.mobile.sample.network.NetworkManager
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class UsersRemoteDataSource
@Inject constructor(private val networkManager: NetworkManager) : UsersDataSource {

    private val asyncJobs: MutableList<Job> = mutableListOf()

    override fun getUsers(onUsersLoaded: OnUsersLoaded, onDataNotAvailable: OnDataNotAvailable) {
        val jobs = launch(UI) {
            try {
                val users = networkManager.getUsers().await()
                if (users.isEmpty()) onDataNotAvailable() else onUsersLoaded(users)
            } catch (exception: Exception) {
                onDataNotAvailable()
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