package com.mobile.sample.dagger

import com.mobile.sample.main.MainActivity
import com.mobile.sample.userDetails.UserDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun userDetailsActivityInjector(): UserDetailsActivity
}