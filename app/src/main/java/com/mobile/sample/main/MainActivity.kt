package com.mobile.sample.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import com.mobile.sample.R
import com.mobile.sample.dagger.ViewModelFactory
import com.mobile.sample.utils.Router
import com.mobile.sample.utils.addDivider
import com.mobile.sample.utils.getViewModel
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
            result?.onSuccess {
                userListAdapter.setItemList(it)
                userListAdapter.submitList(it)
            }?.onFailure {
                Log.d("ERROR", "Error is: ${it.localizedMessage}")
            }
        })

        usersViewModel.getUserClickedEvent().observe(this, Observer { userId ->
            userId?.let {
                Router.navigateToUserDetailsActivity(this, it)
            }
        })
    }
}
