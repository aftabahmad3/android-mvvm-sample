package com.mobile.sample

import android.arch.lifecycle.ViewModel
import com.mobile.sample.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job

open class BaseViewModel(contextProvider: CoroutineContextProvider) : ViewModel(), ViewModelScope {

    private val job = Job()
    override val scope = CoroutineScope(contextProvider.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

interface ViewModelScope {
    val scope: CoroutineScope
}