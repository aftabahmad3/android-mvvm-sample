package com.mobile.sample.main.di

import com.mobile.sample.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [(UserViewModelModule::class)])
    abstract fun MainActivityInjector(): MainActivity

}