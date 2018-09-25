package com.mobile.sample.utils

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL

internal fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, viewModelFactory)[modelClass]
}

internal fun RecyclerView.addDivider() {
    val decoration = DividerItemDecoration(context, VERTICAL)
    addItemDecoration(decoration)
}