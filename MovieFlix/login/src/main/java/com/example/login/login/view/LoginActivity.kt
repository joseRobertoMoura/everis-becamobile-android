package com.example.login.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.login.R
import com.example.login.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity() : AppCompatActivity(), View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel
    private  lateinit var userName: String
    private lateinit var userPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        btn_login.setOnClickListener(this)
    }

    private fun loginViewObeserver() {
        val intent = Intent()
        loginViewModel =
            ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        loginViewModel.init(userName, userPassword)
        loginViewModel.requestLogin.observe(this, { result ->
            if (result) {
                setResult(M_LOGIN_CODE_SUCESS,intent)
                finish()
            } else if (!result) {
                Toast.makeText(this, "Usario/Senha incorretos", Toast.LENGTH_SHORT).show()
                setResult(M_LOGIN_CODE_ERROR,intent)
            }
        })
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_login) {
            userName = edt_userName.text.toString()
            userPassword = edt_userPassword.text.toString()
            if (userName != "" && userPassword != "") {
                loginViewObeserver()
            } else {
                Toast.makeText(this, "Usuario e senha devem ser preenchidos", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    companion object {
        const val M_LOGIN_CODE_SUCESS = 1
        const val M_LOGIN_CODE_ERROR = 2
    }
}