package com.example.data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoModel(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String?,
    @SerialName("birth")
    val birth: LocalDate,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("height")
    val height: Int,
    @SerialName("weight")
    val weight: Int,
    @SerialName("gender")
    val gender: String
)