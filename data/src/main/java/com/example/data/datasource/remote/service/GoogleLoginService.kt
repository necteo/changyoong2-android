package com.example.data.datasource.remote.service

import com.example.data.model.request.LoginGoogleRequest
import com.example.data.model.response.LoginGoogleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleLoginService {
    @POST("token")
    suspend fun googleLogin(
        @Body request: LoginGoogleRequest
    ): Response<LoginGoogleResponse>?
}