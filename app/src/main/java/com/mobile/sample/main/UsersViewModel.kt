package com.mobile.sample.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UsersRepository
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val usersRepository: UsersRepository) : ViewModel() {

    fun getUsers(): LiveData<List<User>> {
        return usersRepository.getUsers()
    }

    fun cleanUpJobs() {
        usersRepository.cleanUpJobs()
    }
}