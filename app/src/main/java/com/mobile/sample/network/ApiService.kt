package com.mobile.sample.network

import com.mobile.sample.data.users.remote.UserRemoteModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {

    @get:GET("users")
    val users: Deferred<List<UserRemoteModel>>

}