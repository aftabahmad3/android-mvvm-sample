package com.mobile.sample.main

import android.arch.lifecycle.LiveData
import com.mobile.sample.BaseViewModel
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.utils.CoroutineContextProvider
import com.mobile.sample.utils.LiveEvent
import com.mobile.sample.utils.Result
import javax.inject.Inject

class UsersViewModel @Inject constructor(
        contextProvider: CoroutineContextProvider,
        usersRepository: UsersRepository
) : BaseViewModel(contextProvider), UserItemActionsListener {

    private val userId = LiveEvent<Int>()
    private val users: LiveData<Result<List<User>>> = usersRepository.getUsers(scope)

    fun getUsers(): LiveData<Result<List<User>>> {
        return users
    }

    fun getUserClickedEvent(): LiveEvent<Int> = userId

    override fun onUserClicked(userId: Int) {
        this.userId.value = userId
    }
}