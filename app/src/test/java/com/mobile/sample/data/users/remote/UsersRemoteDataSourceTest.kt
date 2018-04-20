package com.mobile.sample.data.users.remote

import com.mobile.sample.data.users.OnDataNotAvailable
import com.mobile.sample.data.users.OnUsersLoaded
import com.mobile.sample.network.NetworkManager
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersRemoteDataSourceTest {

    @Mock
    private lateinit var networkManager: NetworkManager

    @Mock
    private lateinit var onUsersLoaded: OnUsersLoaded

    @Mock
    private lateinit var onDataNotAvailable: OnDataNotAvailable

    private lateinit var remoteDataSource: UsersRemoteDataSource

    @Before
    fun setUp() {
        remoteDataSource = UsersRemoteDataSource(networkManager, TestCoroutineContextProvider())
    }

    @Test
    fun getUsers_hasUsers_callOnDataLoaded() {
        val userList = listOf(UserRemoteModel())
        Mockito.`when`(networkManager.getUsers()).thenReturn(CompletableDeferred(userList))

        runBlocking {
            remoteDataSource.getUsers(onUsersLoaded, onDataNotAvailable)
            Mockito.verify(onUsersLoaded).invoke(userList)
        }
    }

    @Test
    fun getUsers_emptyUsers_callOnDataNotAvailable() {
        val userList = emptyList<UserRemoteModel>()
        Mockito.`when`(networkManager.getUsers()).thenReturn(CompletableDeferred(userList))

        runBlocking {
            remoteDataSource.getUsers(onUsersLoaded, onDataNotAvailable)
            Mockito.verify(onDataNotAvailable).invoke()
        }
    }
}