package com.example.data.datasource.remote.service

import com.example.data.model.response.LoginOunmoResponse
import com.example.data.model.request.LoginUser
import com.example.data.model.request.UserInfoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAuthService {

    @POST("oauth/login")
    suspend fun ounmoLogin(): Response<LoginOunmoResponse>?

    @POST("login")
    fun login(
        @Body loginUser: LoginUser
    ): Response<Long>?

    @POST("signup")
    fun signUp(
        @Body userInfoModel: UserInfoModel
    ): Response<String>?
}