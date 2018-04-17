package com.mobile.sample.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL

internal fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory)[modelClass]
}

internal fun RecyclerView.addDivider() {
    val decoration = DividerItemDecoration(context, VERTICAL)
    addItemDecoration(decoration)
}