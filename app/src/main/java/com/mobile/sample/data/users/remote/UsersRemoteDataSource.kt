package com.mobile.sample.data.users.remote

import com.mobile.sample.Mockable
import com.mobile.sample.data.users.UsersDataSource
import com.mobile.sample.network.NetworkManager
import javax.inject.Inject

@Mockable
class UsersRemoteDataSource
@Inject constructor(private val networkManager: NetworkManager) : UsersDataSource {

    override suspend fun getUsers() = networkManager.getUsers().await()

}