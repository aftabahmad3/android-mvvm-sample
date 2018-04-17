package com.mobile.sample.main

import android.support.v7.widget.RecyclerView
import com.mobile.sample.data.users.User
import com.mobile.sample.databinding.ItemUserLayoutBinding

class UserViewHolder(private val viewBinding: ItemUserLayoutBinding,
        private val userItemActionsListener: UserItemActionsListener) : RecyclerView.ViewHolder(viewBinding.root) {

    fun onBind(user: User) {
        viewBinding.user = user
        viewBinding.userActionListener = userItemActionsListener

        viewBinding.executePendingBindings()
    }
}