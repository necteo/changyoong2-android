package com.example.data.datasource.remote

import com.example.data.model.LoginGoogleRequestModel
import com.example.data.model.LoginGoogleResponseModel
import com.example.data.di.RetrofitClass
import com.example.data.datasource.remote.service.LoginService
import retrofit2.Response
import retrofit2.create

class UserRemoteDataSourceImpl() : UserRemoteDataSource {
    private val getAccessTokenBaseUrl = "https://oauth2.googleapis.com"
    private val loginService: LoginService = RetrofitClass.retrofit(getAccessTokenBaseUrl).create()

    override suspend fun loginGoogleUser(
        loginGoogleRequestModel: LoginGoogleRequestModel
    ): Response<LoginGoogleResponseModel> {
        return loginService.getAccessToken(loginGoogleRequestModel)
    }
}