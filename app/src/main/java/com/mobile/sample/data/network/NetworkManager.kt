package com.mobile.sample.data.network

import com.mobile.sample.data.users.User
import kotlinx.coroutines.experimental.Deferred
import javax.inject.Inject

class NetworkManager @Inject constructor(private val service: ApiService) {

    fun getUsers(): Deferred<List<User>> {
        return service.users
    }
}