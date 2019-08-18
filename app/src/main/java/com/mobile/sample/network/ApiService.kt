package com.mobile.sample.network

import com.mobile.sample.data.users.remote.UserRemoteModel
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserRemoteModel>
}