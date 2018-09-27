package com.mobile.sample

import androidx.lifecycle.ViewModel
import com.mobile.sample.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job

open class BaseViewModel(contextProvider: CoroutineContextProvider) : ViewModel() {

    private val job = Job()
    val scope = CoroutineScope(contextProvider.Main + job)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}