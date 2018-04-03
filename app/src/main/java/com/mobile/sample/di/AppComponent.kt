package com.mobile.sample.di

import com.mobile.sample.MainApplication
import com.mobile.sample.data.network.di.NetworkManagerModule
import com.mobile.sample.data.network.users.di.UserRepositoryModule
import com.mobile.sample.data.network.users.di.UsersRemoteDataSourceModule
import com.mobile.sample.main.di.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (NetworkManagerModule::class),
    (UsersRemoteDataSourceModule::class),
    (UserRepositoryModule::class),
    (MainActivityModule::class)
])
interface AppComponent : AndroidInjector<MainApplication> {

    override fun inject(instance: MainApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MainApplication): AppComponent.Builder

        fun build(): AppComponent
    }
}