package com.example.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginOunmoResponse(
    @SerialName("isNewUser")
    val isNewUser: Boolean = false
) {
    companion object {
        val EMPTY = LoginOunmoResponse(false)
    }
}