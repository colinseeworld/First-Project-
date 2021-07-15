package com.example.jsone3_categoryfood.models

data class LoginResponse(

    val token: String,
    val user: User
)

data class User(
    val _id: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String,
    var token: String?,
)