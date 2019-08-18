package com.mobile.sample.network

import com.mobile.sample.data.users.remote.UserRemoteModel
import javax.inject.Inject

class NetworkManager @Inject constructor(private val service: ApiService) {

    suspend fun getUsers(): List<UserRemoteModel> = service.getUsers()
}