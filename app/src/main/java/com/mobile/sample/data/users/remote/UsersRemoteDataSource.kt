package com.mobile.sample.data.users.remote

import com.mobile.sample.data.network.NetworkManager
import com.mobile.sample.data.users.UsersDataSource
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class UsersRemoteDataSource
@Inject constructor(private val networkManager: NetworkManager) : UsersDataSource {

    override fun getUsers(callback: UsersDataSource.LoadUsersCallback) {
        launch(UI) {
            try {
                val users = networkManager.getUsers().await()
                if (users.isEmpty()) callback.onDataNotAvailable() else callback.onUsersLoaded(users)
            } catch (exception: Exception) {
                callback.onDataNotAvailable()
            }
        }
    }
}