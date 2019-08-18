package com.mobile.sample.data.users

import com.mobile.sample.data.users.local.UserLocalModel
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.UserRemoteModel
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import com.mobile.sample.utils.Failure
import com.mobile.sample.utils.Success
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UsersRepositoryTest {

    @Mock lateinit var localDataSource: UsersLocalDataSource
    @Mock lateinit var remoteDataSource: UsersRemoteDataSource

    private lateinit var usersRepository: UsersRepository

    private val user = UserLocalModel(0, "Jon", "Jon Snow")
    private val userList = listOf(user)
    private val remoteUserList = listOf(UserRemoteModel())

    @Before
    fun setUp() {
        usersRepository = UsersRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun getUsers_hasLocalUsers_setUsersLiveData() = runBlockingTest {
        `when`(localDataSource.getUsers()).thenReturn(userList)
        val users = usersRepository.getUsers()

        assertTrue {
            users is Success
        }
    }

    @Test
    fun getUsers_noLocalUsers_fetchFromNetwork() = runBlockingTest {
        // When
        `when`(localDataSource.getUsers()).thenReturn(emptyList())
        `when`(remoteDataSource.getUsers()).thenReturn(remoteUserList)
        val users = usersRepository.getUsers()


        // Then
        verify(localDataSource).getUsers()
        verify(remoteDataSource).getUsers()
        verify(localDataSource).insertUsers(remoteUserList)

        assertTrue {
            users is Success
        }
    }

    @Test
    fun getUsers_fetchLocalUsersError_catchError() = runBlockingTest {
        // Given
        val error = Throwable("error from DB")

        // When
        `when`(localDataSource.getUsers()).thenAnswer { throw error }
        val users = usersRepository.getUsers()

        // Then
        assertTrue {
            users is Failure
        }
    }

    @Test
    fun getUserWithId_getUser_isSuccessful() = runBlockingTest {
        // When
        `when`(localDataSource.getUser(anyInt())).thenReturn(user)
        val result = usersRepository.getUser(anyInt())

        // Then
        assertTrue {
            result is Success
        }
    }

    @Test
    fun getUserWithId_hasError_catchErrorResult() = runBlockingTest {
        // Given
        val error = Throwable("cannot fetch user from DB")

        // When
        `when`(localDataSource.getUser(anyInt())).thenAnswer { throw error }
        val result = usersRepository.getUser(anyInt())

        // Then
        assertTrue {
            result is Failure
        }
    }
}