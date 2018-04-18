package com.mobile.sample.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import com.mobile.sample.utils.LiveEvent
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val usersRepository: UsersRepository) : ViewModel(), UserItemActionsListener {

    private val userId = LiveEvent<Int>()

    fun getUsers(): LiveData<List<User>> {
        return usersRepository.getUsers()
    }

    fun getUserClickedEvent(): LiveEvent<Int> {
        return userId
    }

    override fun onUserClicked(userId: Int) {
        this.userId.value = userId
    }

    override fun onCleared() {
        super.onCleared()
        usersRepository.cleanUpJobs()
    }
}