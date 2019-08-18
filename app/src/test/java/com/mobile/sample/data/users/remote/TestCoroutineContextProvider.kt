package com.mobile.sample.data.users.remote

import com.mobile.sample.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val Main: CoroutineContext = TestCoroutineDispatcher()
    override val IO: CoroutineContext = TestCoroutineDispatcher()
}