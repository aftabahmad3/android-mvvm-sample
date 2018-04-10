package com.mobile.sample.network

import com.mobile.sample.data.users.UserRemoteModel
import kotlinx.coroutines.experimental.Deferred
import javax.inject.Inject

class NetworkManager @Inject constructor(private val service: ApiService) {

    fun getUsers(): Deferred<List<UserRemoteModel>> {
        return service.users
    }
}