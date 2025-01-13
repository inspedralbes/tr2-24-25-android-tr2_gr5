package com.example.supportly.model

data class LoginResponse(
    val message: String,
    val user: UserData? = null
)

data class UserData(
    val id: Int,
    val email: String,
    val password: String,
    val tipus: String
)