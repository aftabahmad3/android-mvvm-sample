package com.mobile.sample.ui.users.details

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mobile.sample.R
import com.mobile.sample.dagger.ViewModelFactory
import com.mobile.sample.databinding.ActivityUserDetailsBinding
import com.mobile.sample.ui.users.UsersViewModel
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

        usersViewModel.viewState.observe(this, Observer { result ->
            return@Observer when(result) {
                is UsersViewModel.ViewState.HasItems -> Unit
                is UsersViewModel.ViewState.HasUser -> binding.user = result.user
                is UsersViewModel.ViewState.Error -> Unit
            }
        })

        if (savedInstanceState == null) {
            usersViewModel.getUserWithId(userId)
        }
    }

    private fun getUserId(): Int = intent.extras?.getInt("userId") ?: 0
}