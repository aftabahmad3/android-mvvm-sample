package com.mobile.sample.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.mobile.sample.R
import com.mobile.sample.dagger.ViewModelFactory
import com.mobile.sample.utils.*
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

        usersViewModel = getViewModel(UsersViewModel::class.java, viewModelFactory)

        val userListAdapter = UserListAdapter(usersViewModel)
        userRecyclerView.addDivider()
        userRecyclerView.adapter = userListAdapter

        usersViewModel.getUsers().observe(this, Observer { result ->
            when (result) {
                is Success -> {
                    userListAdapter.setItemList(result.data)
                    userListAdapter.submitList(result.data)
                }
                is Failure -> {
                    Log.d("ERROR", "Error is: ${result.throwable.localizedMessage}")
                }
            }
        })

        usersViewModel.getUserClickedEvent().observe(this, Observer { userId ->
            userId?.let {
                Router.navigateToUserDetailsActivity(this, it)
            }
        })
    }
}
