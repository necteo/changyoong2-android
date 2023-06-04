package com.example.ounmo.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.ounmo.R
import com.example.ounmo.activity.MainActivity
import com.example.ounmo.activity.Register
import com.example.ounmo.model.LoginGoogleRequestModel
import com.example.ounmo.model.LoginGoogleResponseModel
import com.example.ounmo.retrofit.RetrofitClass
import com.example.ounmo.retrofit.service.LoginService
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LoginRepository(private val context: Context) {

    private val getAccessTokenBaseUrl = "https://oauth2.googleapis.com"
    private val sendAccessTokenBaseUrl = context.getString(R.string.server_base_url)

    fun getAccessToken(account: GoogleSignInAccount?, grant_type: String) {
        val loginService: LoginService = RetrofitClass.retrofit(getAccessTokenBaseUrl).create()
        loginService.getAccessToken(
            request = LoginGoogleRequestModel(
                grant_type = grant_type,
                client_id = context.getString(R.string.server_client_id),
                client_secret = context.getString(R.string.server_client_secret),
                code = account?.serverAuthCode.orEmpty()
            )
        ).enqueue(object : Callback<LoginGoogleResponseModel> {
            override fun onResponse(call: Call<LoginGoogleResponseModel>, response: Response<LoginGoogleResponseModel>) {
                if(response.isSuccessful) {
                    val accessToken = response.body()?.accessToken.orEmpty()
                    val refreshToken = response.body()?.refreshToken.orEmpty()
                    Log.d(TAG, "accessToken: $accessToken")
                    Log.d(TAG, "refreshToken: $refreshToken")
                    MainActivity.preferences.setString("access_token", accessToken)
                    MainActivity.preferences.setString("refresh_token", refreshToken)
                    sendAccessToken()
                }
            }
            override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                Log.e(TAG, "getOnFailure: ",t.fillInStackTrace() )
            }
        })
    }

    fun sendAccessToken() {
        val loginService: LoginService = RetrofitClass.retrofit(sendAccessTokenBaseUrl).create()
        loginService.sendAccessToken().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    Log.d(TAG, "sendOnResponse: ${response.body().toString()}")
                    MainActivity.preferences.setString("user_id", response.body().toString())
                } else {
                    // response.errorBody()?.string()
                    val errorBody = response.errorBody()?.let { getErrorResponse(it) }
                    Log.d(TAG, "sendOnResponseIsNotSuccessful: $errorBody")
                    // 회원 등록 Activity 로 이동
                    val registerIntent = Intent(context, Register::class.java)
                    context.startActivity(registerIntent)
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

    fun getErrorResponse(errorBody: ResponseBody): String {
        return RetrofitClass.retrofit(sendAccessTokenBaseUrl).responseBodyConverter<String>(
            String()::class.java,
            String()::class.java.annotations
        ).convert(errorBody)!!
    }
}