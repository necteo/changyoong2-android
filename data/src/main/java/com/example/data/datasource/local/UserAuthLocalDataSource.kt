package com.example.data.datasource.local

interface UserAuthLocalDataSource {

    suspend fun checkLoginStatus(): Boolean

    suspend fun registerAuthToken(accessToken: String, refreshToken: String)

    suspend fun deleteAuthToken()
}