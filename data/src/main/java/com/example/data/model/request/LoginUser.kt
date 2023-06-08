package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginUser(
    val id: String,
    val pw: String
)
