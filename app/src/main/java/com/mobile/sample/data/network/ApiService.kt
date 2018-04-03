package com.mobile.sample.data.network

import com.mobile.sample.data.network.users.User
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET

interface ApiService {

    @get:GET("users")
    val users: Deferred<List<User>>

}