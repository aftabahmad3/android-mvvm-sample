package com.mobile.sample.ui.users

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

        usersViewModel.viewState.observe(this, Observer { result ->
            return@Observer when (result) {
                is UsersViewModel.ViewState.HasItems -> userListAdapter.setItemList(result.items)
                is UsersViewModel.ViewState.HasUser -> Unit
                is UsersViewModel.ViewState.Error -> {
                    Log.e("TAG", "Error is: ${result.errorMessage}")
                    Unit
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
