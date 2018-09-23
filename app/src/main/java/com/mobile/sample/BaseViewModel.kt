package com.mobile.sample

import android.arch.lifecycle.ViewModel
import com.mobile.sample.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

open class BaseViewModel(contextProvider: CoroutineContextProvider) : ViewModel() {

    private val job = Job()
    val scope = CoroutineScope(contextProvider.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}