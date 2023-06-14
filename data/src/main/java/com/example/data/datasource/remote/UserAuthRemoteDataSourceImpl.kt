package com.example.data.datasource.remote

import android.util.Log
import com.example.data.datasource.local.preferences.PreferenceManager
import com.example.data.datasource.remote.service.GoogleLoginService
import com.example.data.datasource.remote.service.UserAuthService
import com.example.data.mapper.toEntity
import com.example.data.model.request.LoginGoogleRequest
import com.example.data.model.response.LoginGoogleResponse
import com.example.data.model.response.LoginOunmoResponse
import com.example.data.util.DEFAULT_STRING_VALUE
import com.example.data.util.GOOGLE_CLIENT_ID
import com.example.data.util.GOOGLE_CLIENT_SECRET
import com.example.data.util.HTTP_OK
import com.example.data.util.RESPONSE_NULL_ERROR
import com.example.data.util.handleExceptions
import com.example.domain.util.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserAuthRemoteDataSourceImpl @Inject constructor(
    private val userAuthService: UserAuthService,
    private val googleLoginService: GoogleLoginService,
    private val preferences: PreferenceManager,
) : UserAuthRemoteDataSource {

    override suspend fun checkLoginStatus(): Boolean {
        val accessToken = preferences.getAccessToken().first()
        val refreshToken = preferences.getRefreshToken().first()

        return accessToken != DEFAULT_STRING_VALUE && refreshToken != DEFAULT_STRING_VALUE
    }

    override fun ounmoLogin(authToken: String) = flow {
        emit(Result.Loading)
        val response = userAuthService.ounmoLogin() ?: run {
            emit(Result.Error(Exception(RESPONSE_NULL_ERROR)))
            return@flow
        }
        when (response.code()) {
            HTTP_OK -> {
                emit(Result.Success(response.body()?.toEntity() ?: LoginOunmoResponse.EMPTY.toEntity()))
            }

            else -> {
                Log.d("RemoteDataSource-ounmoLogin", "code: ${response.code()}")
                emit(Result.Error(Exception(response.message()), response.code()))
            }
        }
    }.handleExceptions()

    override fun googleLogin(authCode: String) = flow {
        emit(Result.Loading)
        val response = googleLoginService.googleLogin(
            LoginGoogleRequest(
                grant_type = "authorization_code",
                client_id = GOOGLE_CLIENT_ID,
                client_secret = GOOGLE_CLIENT_SECRET,
                code = authCode
            )
        ) ?: run {
            Log.d("RemoteDataSourceImpl-googleLogin", "run: response null error")
            emit(Result.Error(Exception(RESPONSE_NULL_ERROR)))
            return@flow
        }
        if (response.isSuccessful) {
            Log.d("RemoteDataSource-googleLogin", "access token: ${response.body()?.accessToken}")
            Log.d("RemoteDataSource-googleLogin", "refresh token: ${response.body()?.refreshToken}")
            val accessToken = response.body()?.accessToken ?: DEFAULT_STRING_VALUE
            val refreshToken = response.body()?.refreshToken ?: DEFAULT_STRING_VALUE
            preferences.registerAuthToken(accessToken, refreshToken)
            emit(
                Result.Success(
                    response.body()?.toEntity() ?: LoginGoogleResponse.EMPTY.toEntity()
                )
            )
        } else {
            Log.d("RemoteDataSourceImpl-googleLogin", "response is not successful")
            emit(Result.Error(Exception(response.message()), response.code()))
        }
    }.handleExceptions()

//    override fun logout() = flow {
//        emit(Result.Loading)
//
//        val accessToken = runBlocking {
//            preferences.getAccessToken().first()
//        }
//        val response = userAuthService.logout(VALUE_CONTENT_TYPE, accessToken) ?: run {
//            emit(Result.Error(Exception(RESPONSE_NULL_ERROR)))
//            return@flow
//        }
//        when (response.code()) {
//            HTTP_OK -> emit(Result.Success(response.body() ?: DEFAULT_STRING_VALUE))
//            else -> emit(Result.Error(Exception(response.message()), response.code()))
//        }
//    }.handleExceptions()
}