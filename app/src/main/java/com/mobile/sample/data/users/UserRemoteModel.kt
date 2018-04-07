package com.mobile.sample.data.users

data class UserRemoteModel(
        override val id: Int,
        override val name: String,
        override val username: String,
        val email: String,
        val address: Address,
        val phone: String,
        val website: String,
        val company: Company
) : User

data class Company(
        val name: String,
        val catchPhrase: String
)

data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String
)