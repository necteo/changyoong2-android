package com.example.data.datasource.remote

import com.example.data.model.LoginGoogleRequestModel
import com.example.data.model.LoginGoogleResponseModel
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun loginGoogleUser(loginGoogleRequestModel: LoginGoogleRequestModel): Response<LoginGoogleResponseModel>
}