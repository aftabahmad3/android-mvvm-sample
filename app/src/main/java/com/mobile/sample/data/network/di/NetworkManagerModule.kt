package com.mobile.sample.data.network.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.mobile.sample.data.network.ApiService
import com.mobile.sample.data.network.NetworkManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkManagerModule {

    private val BASE_URL = "https://jsonplaceholder.typicode.com"

    @Provides
    @Singleton
    fun providesNetworkManager(): NetworkManager {

        val service = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiService::class.java)

        return NetworkManager(service)
    }
}