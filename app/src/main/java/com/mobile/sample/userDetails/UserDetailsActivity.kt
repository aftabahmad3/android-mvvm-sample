package com.mobile.sample.userDetails

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mobile.sample.R
import com.mobile.sample.dagger.ViewModelFactory
import com.mobile.sample.databinding.ActivityUserDetailsBinding
import com.mobile.sample.main.UsersViewModel
import com.mobile.sample.utils.Failure
import com.mobile.sample.utils.Success
import com.mobile.sample.utils.getViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class UserDetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityUserDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details)

        val userId = getUserId()
        val usersViewModel = getViewModel(UsersViewModel::class.java, viewModelFactory)

        usersViewModel.getUserWithId(userId).observe(this, Observer { result ->
            when (result) {
                is Success -> binding.user = result.data
                is Failure -> Log.e("UserDetailsActivity", "Error is: ${result.throwable}")
            }
        })
    }

    private fun getUserId(): Int = intent.extras?.getInt("userId") ?: 0
}