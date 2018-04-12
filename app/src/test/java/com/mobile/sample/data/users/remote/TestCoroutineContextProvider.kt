package com.mobile.sample.data.users.remote

import com.mobile.sample.utils.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {
    override val Main: CoroutineContext = Unconfined
    override val IO: CoroutineContext = Unconfined
}