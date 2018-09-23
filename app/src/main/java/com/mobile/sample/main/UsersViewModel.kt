package com.mobile.sample.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mobile.sample.BaseViewModel
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.utils.CoroutineContextProvider
import com.mobile.sample.utils.LiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
        contextProvider: CoroutineContextProvider,
        private val usersRepository: UsersRepository
) : BaseViewModel(contextProvider), UserItemActionsListener {

    private val userId = LiveEvent<Int>()
    private val users = MutableLiveData<Result<List<User>>>()

    init {
        scope.launch(contextProvider.IO) {
            usersRepository.getUsers(users)
        }
    }

    fun getUsers(): LiveData<Result<List<User>>> = users

    fun getUserClickedEvent(): LiveEvent<Int> = userId

    override fun onUserClicked(userId: Int) {
        this.userId.value = userId
    }
}