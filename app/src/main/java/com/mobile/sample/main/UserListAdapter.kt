package com.mobile.sample.main

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.UserDiffCallback
import com.mobile.sample.databinding.UserItemBinding

class UserListAdapter(private val userItemActionsListener: UserItemActionsListener)
    : ListAdapter<User, UserViewHolder>(UserDiffCallback()) {

    private var itemList: List<User> = listOf()

    fun setItemList(itemList: List<User>) {
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(layoutInflater, parent, false)
        return UserViewHolder(binding, userItemActionsListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}