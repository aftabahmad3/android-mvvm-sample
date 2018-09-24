package com.mobile.sample.utils

sealed class Result<T> {
    fun get(): T? = null
}

data class Success<T>(val data: T) : Result<T>()
data class Failure<T>(val throwable: Throwable) : Result<T>()
