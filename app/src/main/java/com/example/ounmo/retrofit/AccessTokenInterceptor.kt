package com.example.ounmo.retrofit

import android.util.Log
import com.example.ounmo.repository.PreferenceRepository
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val preferenceRepository: PreferenceRepository
    ): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken: String = preferenceRepository.getString("access_token", "no access token")

        val tokenAddedRequest = chain.request().newBuilder()
            .addHeader("authorization", "Bearer $accessToken")
            .build()
        Log.d("AccessTokenInterceptor", tokenAddedRequest.headers.toString())

        return chain.proceed(tokenAddedRequest)
    }
}