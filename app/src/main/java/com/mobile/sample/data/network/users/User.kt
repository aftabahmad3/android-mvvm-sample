package com.mobile.sample.data.network.users

data class User(
        val id: Int,
        val name: String,
        val username: String,
        val email: String,
        val address: Address,
        val phone: String,
        val website: String,
        val company: Company
)

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