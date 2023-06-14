package com.example.data.datasource.local

import com.example.data.datasource.local.preferences.PreferenceManager
import com.example.data.util.DEFAULT_STRING_VALUE
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserAuthLocalDataSourceImpl @Inject constructor(
    private val preferences: PreferenceManager
) : UserAuthLocalDataSource {
    override suspend fun checkLoginStatus(): Boolean {
        val accessToken = preferences.getAccessToken().first()
        val refreshToken = preferences.getRefreshToken().first()

        return accessToken != DEFAULT_STRING_VALUE && refreshToken != DEFAULT_STRING_VALUE
    }

    override suspend fun registerAuthToken(accessToken: String, refreshToken: String) {
        preferences.registerAuthToken(accessToken, refreshToken)
    }

    override suspend fun deleteAuthToken() {
        preferences.deleteAuthToken()
    }
}