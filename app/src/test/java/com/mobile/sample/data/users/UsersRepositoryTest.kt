package com.mobile.sample.data.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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
import org.mockito.ArgumentMatchers.anyInt
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
    lateinit var remoteDataSource: UsersRemoteDataSource

    @Mock
    lateinit var localDataSource: UsersLocalDataSource

    @Mock
    lateinit var userListObserver: Observer<Result<List<User>>>

    @Mock
    lateinit var userObserver: Observer<Result<User>>

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
        // When
        val usersData = runBlocking {
            `when`(localDataSource.getUsers()).thenReturn(userList)
            usersRepository.getUsers(this)
        }
        usersData.observeForever(userListObserver)

        // Then
        verify(userListObserver).onChanged(Success(userList))
    }

    @Test
    fun getUsers_noLocalUsers_fetchFromNetwork() = runBlocking {
        // When
        val usersData = runBlocking {
            `when`(localDataSource.getUsers()).thenReturn(emptyList())
            `when`(remoteDataSource.getUsers()).thenReturn(remoteUserList)
            usersRepository.getUsers(this)
        }

        usersData.observeForever(userListObserver)

        // Then
        verify(localDataSource).getUsers()
        verify(remoteDataSource).getUsers()
        verify(localDataSource).insertUsers(remoteUserList)
        verify(userListObserver).onChanged(Success(remoteUserList))
    }

    @Test
    fun getUsers_fetchLocalUsersError_catchError() {
        // Given
        val error = Throwable("error from DB")

        // When
        val usersData = runBlocking {
            `when`(localDataSource.getUsers()).thenAnswer { throw error }
            usersRepository.getUsers(this)
        }
        usersData.observeForever(userListObserver)

        // Then
        verify(userListObserver).onChanged(Failure(error))
    }

    @Test
    fun getUserWithId_getUser_setLiveData() {
        // When
        val userData = runBlocking {
            `when`(localDataSource.getUser(anyInt())).thenReturn(user)
            usersRepository.getUser(this, anyInt())
        }
        userData.observeForever(userObserver)

        // Then
        verify(userObserver).onChanged(Success(user))
    }

    @Test
    fun getUserWithId_hasError_catchErrorResult() {
        // Given
        val error = Throwable("cannot fetch user from DB")

        // When
        val userData = runBlocking {
            `when`(localDataSource.getUser(anyInt())).thenAnswer { throw error }
            usersRepository.getUser(this, anyInt())
        }
        userData.observeForever(userObserver)

        // Then
        verify(userObserver).onChanged(Failure(error))
    }
}