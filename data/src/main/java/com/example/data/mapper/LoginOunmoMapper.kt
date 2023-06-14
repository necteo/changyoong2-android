package com.example.data.mapper

import com.example.data.model.response.LoginOunmoResponse
import com.example.domain.entity.LoginOunmoEntity

internal fun LoginOunmoResponse.toEntity() = LoginOunmoEntity(
    isNewUser = isNewUser
)