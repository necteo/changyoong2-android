package com.example.data.util

import com.example.data.datasource.local.preferences.PreferenceManager
import com.example.domain.util.Result
import retrofit2.Response

suspend fun <T, R> Response<T>.handleResponse(
    preferences: PreferenceManager,
    successHandler: (Response<T>) -> R
): Result<R> {
    return when (this.code()) {
        HTTP_OK -> {
            val newAccessToken = this.headers()["Authorization"] ?: DEFAULT_STRING_VALUE
            if (newAccessToken.isNotEmpty()) {
                preferences.updateAccessToken(newAccessToken)
            }
            Result.Success(successHandler(this))
        }

        else -> Result.Error(Exception(this.message()), this.code())
    }
}