package com.mobile.sample.dagger

import com.mobile.sample.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun MainActivityInjector(): MainActivity
}