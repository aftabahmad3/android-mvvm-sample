package com.mobile.sample.dagger

import com.mobile.sample.MainApplication
import com.mobile.sample.data.users.dagger.UserDataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (AppModule::class),
    (ViewModelModule::class),
    (UserDataModule::class),
    (ActivityBuilderModule::class)
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