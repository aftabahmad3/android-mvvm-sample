package com.mobile.sample.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.mobile.sample.R
import com.mobile.sample.dagger.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersViewModel = ViewModelProviders.of(this, viewModelFactory)[UsersViewModel::class.java]

        usersViewModel.getUsers().observe(this, Observer {
            Log.d("MainActivity", "list has: " + it?.toString())
        })
    }
}
