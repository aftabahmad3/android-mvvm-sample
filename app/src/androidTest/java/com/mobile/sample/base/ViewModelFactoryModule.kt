package com.mobile.sample.base

import androidx.lifecycle.ViewModel
import com.mobile.sample.dagger.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun providesViewModelFactory(viewModel: ViewModel): ViewModelFactory {
        return object : ViewModelFactory(mapOf()) {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModel as T
            }
        }
    }
}