package com.mobile.sample.data.users

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.mobile.sample.data.users.local.UserLocalModel
import com.mobile.sample.data.users.local.UsersLocalDataSource
import com.mobile.sample.data.users.remote.TestCoroutineContextProvider
import com.mobile.sample.data.users.remote.UserRemoteModel
import com.mobile.sample.data.users.remote.UsersRemoteDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
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
    private val userListResult = Result.success(userList)
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

        val users = localDataSource.getUsers()
        println("from test" + coroutineContext)
        println(this)
        println(users)

        usersRepository.getUsers(usersData)
        usersData.observeForever(observer)

        // Then
    }

//    @Test
//    fun getUsers_noLocalUsers_fetchFromNetwork() {
//        runBlocking {
//            // Given
//            val usersData = MutableLiveData<Result<List<User>>>()
//
//            // When
//            `when`(localDataSource.getUsers()).thenReturn(emptyList())
//            `when`(remoteDataSource.getUsers()).thenReturn(remoteUserList)
//
//            usersRepository.getUsers(usersData)
//            usersData.observeForever(observer)
//
//            // Then
//            verify(remoteDataSource).getUsers()
//            verify(localDataSource).insertUsers(remoteUserList).await()
//            verify(observer).onChanged(userListResult)
//        }
//    }
}