package com.mobile.sample.data.users.remote

import com.mobile.sample.data.users.User
import kotlinx.serialization.Serializable

@Serializable
data class UserRemoteModel(
        override val id: Int,
        override val name: String,
        override val username: String,
        val email: String,
        val address: Address?,
        val phone: String,
        val website: String,
        val company: Company?
) : User {
    constructor() : this(0, "", "", "", null, "", "", null)
}

@Serializable
data class Company(
        val name: String,
        val catchPhrase: String
)

@Serializable
data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String
)