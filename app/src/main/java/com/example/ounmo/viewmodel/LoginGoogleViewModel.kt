package com.example.ounmo.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.AndroidViewModel
import com.example.ounmo.R
import com.example.ounmo.repository.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task

class LoginGoogleViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.server_client_id))
        .requestScopes(Scope(Scopes.EMAIL), Scope(Scopes.PROFILE))
        // .requestServerAuthCode(context.getString(R.string.server_client_id)) 앱 운영 단계에 사용
        .requestServerAuthCode(context.getString(R.string.server_client_id), true)  // 개발 단계에 사용
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(context, gso)

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            LoginRepository(context).getAccessToken(account, "authorization_code")
        } catch (e: ApiException) {
            Log.w(TAG, "handleSignInResult: error" + e.statusCode)
        }
    }

    fun signIn(activityResultLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent: Intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }

    fun signInSilently() {
        googleSignInClient.silentSignIn().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signedInAccount = task.result
                Log.d(TAG, "Silent Sign In: ${signedInAccount.email}")
                handleSignInResult(task)
            }
        }
    }

    fun signOut(context: Context) {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                Toast.makeText(context, "로그아웃 되셨습니다!", Toast.LENGTH_SHORT).show()
            }
    }

    fun isLogin(context: Context): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return if (account == null) false else (true)
    }

    companion object {
        const val TAG = "GoogleLoginService"
    }
}