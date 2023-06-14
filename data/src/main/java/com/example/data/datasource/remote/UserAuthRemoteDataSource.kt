package com.example.data.datasource.remote

import com.example.domain.entity.LoginGoogleEntity
import com.example.domain.entity.LoginOunmoEntity
import com.example.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface UserAuthRemoteDataSource {
    suspend fun checkLoginStatus(): Boolean

    fun ounmoLogin(authToken: String): Flow<Result<LoginOunmoEntity>>

    fun googleLogin(authCode: String): Flow<Result<LoginGoogleEntity>>

//    fun logout(): Flow<Result<String>>
}