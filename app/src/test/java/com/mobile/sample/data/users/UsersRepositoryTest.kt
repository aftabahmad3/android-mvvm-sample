package com.mobile.sample.data.users

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.mobile.sample.data.users.local.UserLocalModel
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
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

    @Captor
    private lateinit var onUserLoaded: ArgumentCaptor<OnUsersLoaded>

    @Captor
    private lateinit var onDataNotAvailable: ArgumentCaptor<OnDataNotAvailable>

    private lateinit var usersRepository: UsersRepository
    private val userList = listOf(UserLocalModel(0, "Jon", "Jon Snow"))

    @Before
    fun setUp() {
        usersRepository = UsersRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun getUsers_hasLocalUsers_callOnUsersLoaded() {
        val usersData = usersRepository.getUsers()
        usersData.observeForever(observer)

        Mockito.verify(localDataSource).getUsers(capture(onUserLoaded), capture(onDataNotAvailable))
        onUserLoaded.value.invoke(userList)

        Mockito.verify(observer).onChanged(userList)
    }

    @Test
    fun getUsers_noLocalUsers_fetchFromNetwork_callOnUsersLoaded() {
        val usersData = usersRepository.getUsers()
        usersData.observeForever(observer)

        Mockito.verify(localDataSource).getUsers(capture(onUserLoaded), capture(onDataNotAvailable))
        onDataNotAvailable.value.invoke()

        Mockito.verify(remoteDataSource).getUsers(capture(onUserLoaded), capture(onDataNotAvailable))

        onUserLoaded.value.invoke(userList)

        Mockito.verify(observer).onChanged(userList)
    }

    @Test
    fun getUsers_noLocalUsers_fetchFromNetwork_callOnDataNotAvailable() {
        val usersData = usersRepository.getUsers()
        usersData.observeForever(observer)

        Mockito.verify(localDataSource).getUsers(capture(onUserLoaded), capture(onDataNotAvailable))
        onDataNotAvailable.value.invoke()

        Mockito.verify(remoteDataSource).getUsers(capture(onUserLoaded), capture(onDataNotAvailable))

        onDataNotAvailable.value.invoke()

        Mockito.verify(observer).onChanged(emptyList())
    }

    @Test
    fun cleanUpJobs() {
        usersRepository.cleanUpJobs()

        Mockito.verify(localDataSource).cleanupJobs()
        Mockito.verify(remoteDataSource).cleanupJobs()
    }

    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}