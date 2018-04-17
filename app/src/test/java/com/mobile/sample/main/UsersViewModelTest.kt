package com.mobile.sample.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
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

    @Mock
    private lateinit var userListObserver: Observer<List<User>>

    private lateinit var userViewModel: UsersViewModel

    @Before
    fun setUp() {
        userViewModel = UsersViewModel(repository)
    }

    @Test
    fun onUserClicked_callObserverOnChanged() {
        userViewModel.onUserClicked(Mockito.anyInt())

        val userClickedEvent = userViewModel.getUserClickedEvent()
        userClickedEvent.observeForever(observer)

        Mockito.verify(observer).onChanged(Mockito.anyInt())
    }

    @Test
    fun getUsers_hasData_callObserverOnChanged() {
        val data = MutableLiveData<List<User>>()
        data.value = emptyList()
        Mockito.`when`(repository.getUsers()).thenReturn(data)

        val users = userViewModel.getUsers()
        users.observeForever(userListObserver)

        Mockito.verify(userListObserver).onChanged(emptyList())
    }
}