package com.mobile.sample.data.network

import com.mobile.sample.data.network.users.User
import javax.inject.Inject

class NetworkManager @Inject constructor(private val service: ApiService) {

    suspend fun getUsers(): List<User> {
        return service.users.await()
    }
}