package com.example.ounmo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.ounmo.R


class RegisterActivity : AppCompatActivity() {

    private lateinit var button_finalRegister : Button
    private lateinit var editText_userId : EditText
    private lateinit var button_checkId : Button
    private lateinit var editText_userPw : EditText
    private lateinit var editText_nickname : EditText
    private lateinit var editText_height : EditText
    private lateinit var editText_weight : EditText
    private lateinit var button_checkNickname : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        editText_userId = findViewById(R.id.editText_userId)
        editText_userPw = findViewById(R.id.editText_userPw)
        editText_nickname = findViewById(R.id.editText_nickname)
        editText_height = findViewById(R.id.editText_height)
        editText_weight = findViewById(R.id.editText_weight)

        button_finalRegister = findViewById(R.id.button_finalRegister)
        button_checkId = findViewById(R.id.button_checkId)
        button_checkNickname = findViewById(R.id.button_checkNickname)

        var userId:String = editText_userId.getText().toString()
        var userPw:String = editText_userPw.getText().toString()
        var nickname:String = editText_nickname.getText().toString()
        var height:String = editText_height.getText().toString()
        var weight:String = editText_weight.getText().toString()

        button_finalRegister.setOnClickListener{
            finish()
        }


    }
}