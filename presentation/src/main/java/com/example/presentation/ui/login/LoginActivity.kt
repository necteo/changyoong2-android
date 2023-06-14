package com.example.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.R
import com.example.presentation.ui.NaviActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var text_loginTitle : TextView
    private lateinit var button_login : Button
    private lateinit var editText_loginId : EditText
    private lateinit var editText_loginPw : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestScopes(Scope(Scopes.EMAIL), Scope(Scopes.PROFILE))
            // .requestServerAuthCode(context.getString(R.string.server_client_id)) 앱 운영 단계에 사용
            .requestServerAuthCode(getString(R.string.server_client_id), true)  // 개발 단계에 사용
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        text_loginTitle = findViewById(R.id.text_loginTitle)
        button_login = findViewById(R.id.button_login)
        editText_loginId = findViewById(R.id.editText_loginId)
        editText_loginPw = findViewById(R.id.editText_loginPw)

        button_login.setOnClickListener {

            var userId:String = editText_loginId.getText().toString();
            var password:String = editText_loginPw.getText().toString();

            //if(){ }

            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

            val profileIntent = Intent(this, NaviActivity::class.java)
            startActivity(profileIntent)
        }

        val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d(TAG, "resultCode : ${it.resultCode}")
            if (it.resultCode == -1) {
                // TODO it.resultCode에 따라 다른 행동
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                handleSignInResult(task)
                // TODO 결과에 상관없이 메인 화면으로 이동
                val naviIntent = Intent(this, NaviActivity::class.java)
                startActivity(naviIntent)
            }
        }
        val btnGoogleSignIn = findViewById<SignInButton>(R.id.sign_in_button)
        btnGoogleSignIn.setOnClickListener {
            signIn(loginLauncher)
        }
        signInSilently()
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val authCode = completedTask.getResult(ApiException::class.java)?.serverAuthCode
            authCode?.run {
                Log.d(TAG, "authCode: $authCode")
                viewModel.googleLogin(this)
            } ?: Log.w(TAG, "handleSignInResult: error")
        } catch (e: ApiException) {
            Log.w(TAG, "handleSignInResult: error" + e.statusCode)
        }
    }

    private fun signIn(activityResultLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent: Intent = googleSignInClient.signInIntent
        activityResultLauncher.launch(signInIntent)
    }

    private fun signInSilently() {
        googleSignInClient.silentSignIn().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signedInAccount = task.result
                Log.d(TAG, "Silent Sign In: ${signedInAccount.email}")
                handleSignInResult(task)
            }
        }
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                Toast.makeText(this, "로그아웃 되셨습니다!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun isLogin(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        return if (account == null) false else (true)
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}


