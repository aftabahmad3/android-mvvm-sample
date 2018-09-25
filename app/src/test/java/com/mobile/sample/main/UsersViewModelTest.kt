package com.mobile.sample.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.data.users.remote.TestCoroutineContextProvider
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersViewModelTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: UsersRepository

    @Mock
    private lateinit var observer: Observer<Int>

    private lateinit var userViewModel: UsersViewModel

    @Before
    fun setUp() {
        userViewModel = UsersViewModel(TestCoroutineContextProvider(), repository)

    }

    @Test
    fun onUserClicked_callObserverOnChanged() {
        // When
        userViewModel.onUserClicked(anyInt())
        val userClickedEvent = userViewModel.getUserClickedEvent()
        userClickedEvent.observeForever(observer)

        // Then
        verify(observer).onChanged(anyInt())
    }
}