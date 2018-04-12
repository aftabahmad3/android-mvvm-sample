package com.mobile.sample.data.users.local

import com.mobile.sample.data.users.OnDataNotAvailable
import com.mobile.sample.data.users.OnUsersLoaded
import com.mobile.sample.data.users.remote.TestCoroutineContextProvider
import com.mobile.sample.database.AppDatabase
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersLocalDataSourceTest {

    @Mock
    lateinit var database: AppDatabase

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var onDataNotAvailable: OnDataNotAvailable

    @Mock
    lateinit var onUsersLoaded: OnUsersLoaded

    private lateinit var localDataSource: UsersLocalDataSource

    @Before
    fun setUp() {
        localDataSource = UsersLocalDataSource(database, TestCoroutineContextProvider())
        Mockito.`when`(database.userDao()).thenReturn(userDao)
    }

    @Test
    fun getUsers_hasUsers_callOnUsersLoaded() {
        val userList = listOf(UserLocalModel(0, "", ""))
        Mockito.`when`(userDao.getUsers()).thenReturn(userList)

        runBlocking {
            localDataSource.getUsers(onUsersLoaded, onDataNotAvailable)
            Mockito.verify(onUsersLoaded).invoke(userList)
        }
    }

    @Test
    fun getUsers_noUsers_callOnDataNotAvailable() {
        Mockito.`when`(userDao.getUsers()).thenReturn(listOf())
        runBlocking {
            localDataSource.getUsers(onUsersLoaded, onDataNotAvailable)
            Mockito.verify(onDataNotAvailable).invoke()
        }
    }

    @Test
    fun insertUsers_insertSuccessful() {
        runBlocking {
            localDataSource.insertUsers(listOf(), onDataNotAvailable)
            Mockito.verify(database.userDao()).insertUsers(listOf())
            Mockito.verify(onDataNotAvailable, never()).invoke()
        }
    }
}