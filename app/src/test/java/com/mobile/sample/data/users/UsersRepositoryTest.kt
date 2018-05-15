package com.mobile.sample.data.users

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.mobile.sample.data.users.local.UserLocalModel
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.TestCoroutineContextProvider
import com.mobile.sample.data.users.remote.UserRemoteModel
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersRepositoryTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteDataSource: UsersRemoteDataSource

    @Mock
    private lateinit var localDataSource: UsersLocalDataSource

    @Mock
    private lateinit var observer: Observer<List<User>>

    @Mock
    private lateinit var userObserver: Observer<User>

    private lateinit var usersRepository: UsersRepository

    private val user = UserLocalModel(0, "Jon", "Jon Snow")
    private val userList = listOf(user)
    private val remoteUserList = listOf(UserRemoteModel())

    @Before
    fun setUp() {
        usersRepository = UsersRepository(TestCoroutineContextProvider(), remoteDataSource, localDataSource)
    }

    @Test
    fun getUsers_hasLocalUsers_setUsersLiveData() {
        runBlocking {
            Mockito.`when`(localDataSource.getUsers()).thenReturn(userList)

            val usersData = usersRepository.getUsers()
            usersData.observeForever(observer)

            Mockito.verify(observer).onChanged(userList)
        }
    }

    @Test
    fun getUsers_noLocalUsers_fetchFromNetwork() {
        runBlocking {
            Mockito.`when`(localDataSource.getUsers()).thenReturn(emptyList())
            Mockito.`when`(remoteDataSource.getUsers()).thenReturn(remoteUserList)

            val usersData = usersRepository.getUsers()
            usersData.observeForever(observer)

            Mockito.verify(remoteDataSource).getUsers()
            Mockito.verify(localDataSource).insertUsers(remoteUserList)
            Mockito.verify(observer).onChanged(remoteUserList)
        }
    }

    @Test
    fun getUserWithId_hasUser_setUserLiveData() {
        runBlocking {
            Mockito.`when`(localDataSource.getUser(anyInt())).thenReturn(user)

            val usersData = usersRepository.getUser(anyInt())
            usersData.observeForever(userObserver)

            Mockito.verify(userObserver).onChanged(user)
        }
    }
}