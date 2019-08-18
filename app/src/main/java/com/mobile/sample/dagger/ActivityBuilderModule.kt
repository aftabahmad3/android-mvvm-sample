package com.mobile.sample.dagger

import com.mobile.sample.ui.users.MainActivity
import com.mobile.sample.ui.users.details.UserDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun userDetailsActivityInjector(): UserDetailsActivity
}