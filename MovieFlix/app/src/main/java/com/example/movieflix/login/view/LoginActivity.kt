package com.example.movieflix.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movieflix.R
import com.example.movieflix.login.viewmodel.LoginViewModel
import com.example.movieflix.view.MainActivity
import com.example.movieflix.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity() : AppCompatActivity(), View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel
    private var userName = ""
    private var userPassword = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        btn_login.setOnClickListener(this)
    }

    private fun loginViewObeserver() {
        loginViewModel =
            ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        loginViewModel.init(userName, userPassword)
        loginViewModel.requestLogin.observe(this, { result ->
            if (result) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else if (!result) {
                Toast.makeText(this, "Usario/Senha incorretos", Toast.LENGTH_LONG).show()
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
}