package com.example.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginGoogleRequest(
    @SerialName("grant_type")
    private val grant_type: String,
    @SerialName("client_id")
    private val client_id: String,
    @SerialName("client_secret")
    private val client_secret: String,
    @SerialName("code")
    private val code: String
)
