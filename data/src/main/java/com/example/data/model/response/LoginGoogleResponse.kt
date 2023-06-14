package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginGoogleResponse(
    @SerialName("access_token")
    val accessToken: String = "",
    @SerialName("expires_in")
    val expiresIn: Int = 0,
    @SerialName("scope")
    val scope: String = "",
    @SerialName("token_type")
    val tokenType: String = "",
    @SerialName("id_token")
    val idToken: String = "",
    @SerialName("refresh_token")
    val refreshToken: String = ""
) {
    companion object {
        val EMPTY = LoginGoogleResponse("", 0, "","", "", "")
    }
}