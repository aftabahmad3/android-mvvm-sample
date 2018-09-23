package com.mobile.sample.userDetails

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.mobile.sample.R
import com.mobile.sample.dagger.ViewModelFactory
import com.mobile.sample.databinding.ActivityUserDetailsBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class UserDetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityUserDetailsBinding>(this, R.layout.activity_user_details)

    }

    private fun getUserId(): Int = intent.extras?.getInt("userId") ?: 0

}