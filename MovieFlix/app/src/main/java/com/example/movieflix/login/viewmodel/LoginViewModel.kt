package com.example.movieflix.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private var mUserName = ""
    private var mUserPassword = ""
    private var login = MutableLiveData<Boolean>()

    val requestLogin: LiveData<Boolean>
        get() = login

    fun init(userName: String, userPassword: String){
        mUserName = userName
        mUserPassword = userPassword
        checkLogin(mUserName, mUserPassword)
    }

    private fun checkLogin(userName: String, userPassword: String) {

        if (userName == "teste" && userPassword == "teste"){
            login.postValue(true)
        }else {
            login.postValue(false)
        }
    }
}