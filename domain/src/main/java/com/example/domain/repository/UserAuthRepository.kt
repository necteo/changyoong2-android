package com.example.domain.repository

import com.example.domain.entity.LoginGoogleEntity
import com.example.domain.entity.LoginOunmoEntity
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {

    suspend fun checkLoginStatus(): Boolean

    fun ounmoLogin(authToken: String): Flow<Result<LoginOunmoEntity>>

    fun googleLogin(authCode: String): Flow<Result<LoginGoogleEntity>>

//    fun logout(): Flow<Result<String>>

    suspend fun registerAuthToken(accessToken: String, refreshToken: String)

    suspend fun deleteAuthToken()
}