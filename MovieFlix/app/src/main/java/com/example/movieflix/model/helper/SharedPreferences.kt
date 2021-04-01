package com.example.movieflix.model.helper

import android.content.Context

class SharedPreferences(context: Context) {

    private val mSharedPreferences = context.getSharedPreferences("APP", Context.MODE_PRIVATE)

    fun storeString(key: String, Value: String){
        mSharedPreferences.edit().putString(key,Value).apply()
    }

    fun getString(key: String): String{
        return mSharedPreferences.getString(key, "") ?: ""
    }
}