package com.mobile.sample.userDetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(private val usersRepository: UsersRepository) : ViewModel() {

    fun loadUserWithId(userId: Int): LiveData<User> {
        return usersRepository.getUser(userId)
    }

    override fun onCleared() {
        super.onCleared()
        usersRepository.cleanUpJobs()
    }
}