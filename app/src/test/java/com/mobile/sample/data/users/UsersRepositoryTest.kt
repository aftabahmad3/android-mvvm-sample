package com.mobile.sample.data.users

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.mobile.sample.data.users.local.UserLocalModel
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.TestCoroutineContextProvider
import com.mobile.sample.data.users.remote.UserRemoteModel
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import com.mobile.sample.utils.Failure
import com.mobile.sample.utils.Result
import com.mobile.sample.utils.Success
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
    private lateinit var observer: Observer<Result<List<User>>>

    private lateinit var usersRepository: UsersRepository

    private val user = UserLocalModel(0, "Jon", "Jon Snow")
    private val userList = listOf(user)
    private val remoteUserList = listOf(UserRemoteModel())

    @Before
    fun setUp() {
        usersRepository = UsersRepository(TestCoroutineContextProvider(), remoteDataSource, localDataSource)
    }

    @Test
    fun getUsers_hasLocalUsers_setUsersLiveData() = runBlocking {
        // Given
        val usersData = MutableLiveData<Result<List<User>>>()

        // When
        `when`(localDataSource.getUsers()).thenReturn(userList)
        usersRepository.getUsers(usersData)
        usersData.observeForever(observer)

        // Then
        verify(observer).onChanged(Success(userList))
    }

    @Test
    fun getUsers_noLocalUsers_fetchFromNetwork() = runBlocking {
        // Given
        val usersData = MutableLiveData<Result<List<User>>>()

        // When
        `when`(localDataSource.getUsers()).thenReturn(emptyList())
        `when`(remoteDataSource.getUsers()).thenReturn(remoteUserList)

        usersRepository.getUsers(usersData)
        usersData.observeForever(observer)

        // Then
        verify(localDataSource).getUsers()
        verify(remoteDataSource).getUsers()
        verify(localDataSource).insertUsers(remoteUserList)
        verify(observer).onChanged(Success(remoteUserList))
    }

    @Test
    fun getUsers_fetchLocalUsersError_catchError() = runBlocking {
        // Given
        val usersData = MutableLiveData<Result<List<User>>>()
        val error = Throwable("error from DB")

        // When
        `when`(localDataSource.getUsers()).thenAnswer { throw error }
        usersRepository.getUsers(usersData)
        usersData.observeForever(observer)

        // Then
        verify(observer).onChanged(Failure(error))
    }
}