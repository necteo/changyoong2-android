package com.example.ounmo.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ounmo.R
import com.example.ounmo.repository.PreferenceRepository
import com.example.ounmo.viewmodel.LoginGoogleViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton


class LoginActivity : AppCompatActivity() {
    private lateinit var loginGoogleViewModel: LoginGoogleViewModel

    private lateinit var text_loginTitle : TextView
    private lateinit var text_register : TextView
    private lateinit var button_login : Button
    private lateinit var editText_loginId : EditText
    private lateinit var editText_loginPw : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceRepository(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        text_loginTitle = findViewById(R.id.text_loginTitle)
        text_register = findViewById(R.id.text_register)
        button_login = findViewById(R.id.button_login)
        editText_loginId = findViewById(R.id.editText_loginId)
        editText_loginPw = findViewById(R.id.editText_loginPw)

        button_login.setOnClickListener {

            var userId:String = editText_loginId.getText().toString();
            var password:String = editText_loginPw.getText().toString();

            //if(){ }

            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

            val profileIntent = Intent(this, ProfileActivity::class.java)
            startActivity(profileIntent)
        }

        text_register.setOnClickListener {

            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }

        loginGoogleViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[LoginGoogleViewModel::class.java]
        val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d(LoginGoogleViewModel.TAG, "resultCode : ${it.resultCode}")
            // TODO it.resultCode에 따라 다른 행동
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            loginGoogleViewModel.handleSignInResult(task)
            // 결과값에 따라 회원 등록 Activity 로 이동
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
            // TODO 결과에 상관없이 메인 화면으로 이동
        }
        val btnGoogleSignIn = findViewById<SignInButton>(R.id.sign_in_button)
        btnGoogleSignIn.setOnClickListener {
            loginGoogleViewModel.signIn(activityResultLauncher)
        }
        loginGoogleViewModel.signInSilently()
    }

    companion object {
        lateinit var preferences: PreferenceRepository
    }
}


