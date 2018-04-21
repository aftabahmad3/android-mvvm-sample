package com.mobile.sample.dagger

import android.arch.lifecycle.ViewModel
import com.mobile.sample.main.UsersViewModel
import com.mobile.sample.userDetails.UserDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    internal abstract fun bindUsersViewModel(usersViewModel: UsersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
    internal abstract fun bindUserDetailsViewModel(userDetailsViewModel: UserDetailsViewModel): ViewModel
}