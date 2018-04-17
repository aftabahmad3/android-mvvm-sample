package com.mobile.sample.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val usersRepository: UsersRepository) : ViewModel(), UserItemActionsListener {

    private val userId = MutableLiveData<Int>()

    fun getUsers(): LiveData<List<User>> {
        return usersRepository.getUsers()
    }

    fun getUserClickedEvent(): LiveData<Int> {
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