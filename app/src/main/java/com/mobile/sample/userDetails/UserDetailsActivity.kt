package com.mobile.sample.userDetails

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.mobile.sample.R
import com.mobile.sample.dagger.ViewModelFactory
import com.mobile.sample.databinding.ActivityUserDetailsBinding
import com.mobile.sample.utils.getViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class UserDetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var userDetailsViewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityUserDetailsBinding>(this, R.layout.activity_user_details)

        userDetailsViewModel = getViewModel(UserDetailsViewModel::class.java, viewModelFactory)
        userDetailsViewModel.loadUserWithId(getUserId()).observe(this, Observer {
            supportActionBar?.title = it?.name
            binding.user = it
        })
    }

    private fun getUserId(): Int {
        return intent.extras?.getInt("userId") ?: 0
    }
}