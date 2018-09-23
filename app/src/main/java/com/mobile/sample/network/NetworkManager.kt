package com.mobile.sample.network

import com.mobile.sample.Mockable
import com.mobile.sample.data.users.remote.UserRemoteModel
import kotlinx.coroutines.Deferred
import javax.inject.Inject

@Mockable
class NetworkManager @Inject constructor(private val service: ApiService) {

    fun getUsers(): Deferred<List<UserRemoteModel>> = service.users

}