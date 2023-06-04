package com.example.ounmo.activity


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ounmo.LoginGoogle
import com.example.ounmo.R
import com.example.ounmo.repository.PreferenceRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton


class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceRepository(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 로그인 버튼
        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {

            //editText로부터 입력된 값을 받아온다
            val editId = findViewById<EditText>(R.id.edit_id)
            var id = editId.text.toString()
            val editPw = findViewById<EditText>(R.id.edit_pw)
            var pw = editPw.text.toString()

            // 쉐어드로부터 저장된 id, pw 가져오기
            val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
            val savedId = sharedPreference.getString("id", "")
            val savedPw = sharedPreference.getString("pw", "")

            // 유저가 입력한 id, pw값과 쉐어드로 불러온 id, pw값 비교
            if(id == savedId && pw == savedPw){
                // 로그인 성공 다이얼로그 보여주기
                pushDialog("success")
            }
            else{
                // 로그인 실패 다이얼로그 보여주기
                pushDialog("fail")
            }
        }

        // 회원가입 버튼
        val btnRegister = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val loginGoogle = LoginGoogle(this)
        val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d(LoginGoogle.TAG, "resultCode : ${it.resultCode}")
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            loginGoogle.handleSignInResult(task)
        }
        val btnGoogleSignIn = findViewById<SignInButton>(R.id.sign_in_button)
        btnGoogleSignIn.setOnClickListener {
            loginGoogle.signIn(activityResultLauncher)
        }
        loginGoogle.signInSilently()
    }

    // 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
    private fun pushDialog(type: String){
        val dialog = AlertDialog.Builder(this)

        if(type == "success"){
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 성공!")
        }
        else if(type == "fail"){
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인해주세요")
        }

        val dialogListener = DialogInterface.OnClickListener { _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE ->
                    Log.d(TAG, "")
            }
        }

        dialog.setPositiveButton("확인",dialogListener)
        dialog.show()
    }

    companion object {
        lateinit var preferences: PreferenceRepository
    }
}