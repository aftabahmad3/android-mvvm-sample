package com.mobile.sample.utils

import android.content.Context
import android.content.Intent
import com.mobile.sample.ui.users.details.UserDetailsActivity

object Router {

    fun navigateToUserDetailsActivity(context: Context, userId: Int) {
        val intent = Intent(context, UserDetailsActivity::class.java)
        intent.putExtra("userId", userId)
        context.startActivity(intent)
    }
}