package com.mobile.sample.main

import androidx.lifecycle.LiveData
import com.mobile.sample.BaseViewModel
import com.mobile.sample.Mockable
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.utils.CoroutineContextProvider
import com.mobile.sample.utils.LiveEvent
import com.mobile.sample.utils.Result
import javax.inject.Inject

@Mockable
class UsersViewModel @Inject constructor(
        contextProvider: CoroutineContextProvider,
        private val usersRepository: UsersRepository
) : BaseViewModel(contextProvider), UserItemActionsListener {

    private val userId = LiveEvent<Int>()
    private val users: LiveData<Result<List<User>>> = usersRepository.getUsers(scope)

    fun getUsers() = users

    fun getUserWithId(userId: Int) = usersRepository.getUser(scope, userId)

    fun getUserClickedEvent() = userId

    override fun onUserClicked(userId: Int) {
        this.userId.value = userId
    }
}