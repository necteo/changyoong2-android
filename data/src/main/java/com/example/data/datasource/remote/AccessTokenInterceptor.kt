package com.example.data.di

import android.util.Log
import com.example.data.datasource.local.preferences.PreferenceManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val preferences: PreferenceManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken: String = runBlocking {
            preferences.getAccessToken().first()
        }
        val tokenAddedRequest = chain.request().newBuilder()
            .addHeader("authorization", "Bearer $accessToken")
            .build()
        Log.d("AccessTokenInterceptor", tokenAddedRequest.headers.toString())

        return chain.proceed(tokenAddedRequest)
    }
}