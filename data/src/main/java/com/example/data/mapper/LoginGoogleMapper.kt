package com.example.data.mapper

import com.example.data.model.response.LoginGoogleResponse
import com.example.domain.entity.LoginGoogleEntity

internal fun LoginGoogleResponse.toEntity() = LoginGoogleEntity(
    accessToken = accessToken,
    expiresIn = expiresIn,
    scope = scope,
    tokenType = tokenType,
    idToken = idToken
)