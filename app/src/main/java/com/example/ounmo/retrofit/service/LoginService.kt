package com.example.ounmo.retrofit.service

import com.example.ounmo.model.LoginGoogleRequestModel
import com.example.ounmo.model.LoginGoogleResponseModel
import com.example.ounmo.model.LoginUser
import com.example.ounmo.model.UserInfoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("token")
    fun getAccessToken(
        @Body request: LoginGoogleRequestModel
    ): Call<LoginGoogleResponseModel>

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