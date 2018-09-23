package com.mobile.sample.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mobile.sample.MainApplication
import com.mobile.sample.database.AppDatabase
import com.mobile.sample.network.ApiService
import com.mobile.sample.network.NetworkManager
import com.mobile.sample.utils.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    private val DATABASE_NAME = "sample_app_database"
    private val BASE_URL = "https://jsonplaceholder.typicode.com"

    @Provides
    @Singleton
    fun provideContext(application: MainApplication): Context {
        return application
    }

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

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    fun providesCoroutineContext() = CoroutineContextProvider()
}