package com.example.data.datasource.remote.service

import com.example.data.model.LoginGoogleRequestModel
import com.example.data.model.LoginGoogleResponseModel
import com.example.data.model.LoginUser
import com.example.data.model.UserInfoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("token")
    fun getAccessToken(
        @Body request: LoginGoogleRequestModel
    ): Response<LoginGoogleResponseModel>

    @POST("oauth/login")
    fun sendAccessToken(): Call<String>

    @POST("login")
    fun login(
        @Body loginUser: LoginUser
    ): Call<Long>

    @POST("signup")
    fun signUp(
        @Body userInfoModel: UserInfoModel
    ): Call<String>
}