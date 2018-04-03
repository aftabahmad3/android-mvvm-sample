package com.mobile.sample.data.network.users.remote

import com.mobile.sample.data.network.NetworkManager
import com.mobile.sample.data.network.users.UsersDataSource
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class UsersRemoteDataSource
@Inject constructor(private val networkManager: NetworkManager) : UsersDataSource {

    private val uiContext: CoroutineContext = UI


    override fun getUsers(callback: UsersDataSource.LoadUsersCallback) {

        async(UI) {
            val users = networkManager.getUsers()
            if (users.isEmpty()) callback.onDataNotAvailable() else callback.onUsersLoaded(users)
        }

        launch {
            val users = networkManager.getUsers()
            if (users.isEmpty()) callback.onDataNotAvailable() else callback.onUsersLoaded(users)
        }
    }
}