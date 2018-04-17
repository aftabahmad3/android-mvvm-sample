package com.mobile.sample.utils

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL

fun RecyclerView.addDivider() {
    val decoration = DividerItemDecoration(context, VERTICAL)
    addItemDecoration(decoration)
}