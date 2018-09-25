package com.mobile.sample.data.users

import androidx.recyclerview.widget.DiffUtil

interface User {

    val id: Int
    val name: String
    val username: String
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}