package com.mobile.sample.base

import androidx.lifecycle.ViewModel
import com.mobile.sample.dagger.ActivityBuilderModule
import com.mobile.sample.dagger.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    ActivityBuilderModule::class
])
interface TestComponent : AndroidInjector<TestApplication> {

    override fun inject(instance: TestApplication)

    fun getViewModelFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TestApplication): TestComponent.Builder

        @BindsInstance
        fun viewModel(viewModel: ViewModel): TestComponent.Builder

        fun build(): TestComponent
    }
}