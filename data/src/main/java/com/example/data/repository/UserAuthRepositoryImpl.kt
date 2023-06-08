package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.R
import com.example.data.datasource.remote.UserAuthRemoteDataSource
import com.example.data.model.LoginGoogleRequest
import com.example.data.model.LoginGoogleResponse
import com.example.domain.util.APIResponse
import com.example.data.datasource.remote.service.GoogleLoginService
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LoginRepository(
    private val userAuthRemoteDataSource: UserAuthRemoteDataSource,
    private val context: Context
) {
    private val sendAccessTokenBaseUrl = context.getString(R.string.server_base_url)

    suspend fun getAccessToken(
        account: GoogleSignInAccount?,
        grant_type: String
    ): APIResponse<LoginGoogleResponse> {
        val response = userAuthRemoteDataSource.loginGoogleUser(
            loginGoogleRequestModel = LoginGoogleRequest(
                grant_type = grant_type,
                client_id = context.getString(R.string.server_client_id),
                client_secret = context.getString(R.string.server_client_secret),
                code = account?.serverAuthCode.orEmpty()
            )
        )
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return APIResponse.Success(result)
            }
        }
        return APIResponse.Error(response.message())
    }

    fun sendAccessToken() {
        val loginService: GoogleLoginService = RetrofitClass.retrofit(sendAccessTokenBaseUrl).create()
        loginService.sendAccessToken().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    Log.d(TAG, "sendOnResponse: ${response.body().toString()}")
                } else {
                    // response.errorBody()?.string()
                    val errorBody = response.errorBody()?.string()
                    Log.d(TAG, "sendOnResponseIsNotSuccessful: $errorBody")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "sendOnFailure: ${t.fillInStackTrace()}")
            }
        })
    }

    companion object {
        const val TAG = "LoginRepository"
    }
}