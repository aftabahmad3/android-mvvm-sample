package com.mobile.sample.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.mobile.sample.R
import com.mobile.sample.dagger.ViewModelFactory
import com.mobile.sample.utils.addDivider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersViewModel = ViewModelProviders.of(this, viewModelFactory)[UsersViewModel::class.java]

        val userListAdapter = UserListAdapter(usersViewModel)
        userRecyclerView.addDivider()
        userRecyclerView.adapter = userListAdapter

        usersViewModel.getUsers().observe(this, Observer {
            Log.d("MainActivity", "list has: " + it?.toString())
            it?.let {
                userListAdapter.setItemList(it)
                userListAdapter.submitList(it)
            }
        })

        usersViewModel.getUserClickedEvent().observe(this, Observer {
            it?.let {
                Toast.makeText(this, "Clicked on user with id: $it", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
