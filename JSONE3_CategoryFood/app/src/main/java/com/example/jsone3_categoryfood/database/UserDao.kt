package com.example.jsone3_categoryfood.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.edit
import com.example.jsone3_categoryfood.MainApplication
import com.example.jsone3_categoryfood.models.User
import com.google.gson.Gson


object UserDao {

    @SuppressLint("CommitPrefEdits")
    fun setUser(user: User) {
        sharedPreferences().edit {
            putString("user_json", Gson().toJson(user))
        }
    }

    fun getUser(): User? {
        return if (isLogin()) {
            val json = sharedPreferences().getString("user_json", "")
            Gson().fromJson(json, User::class.java)
        } else {
            null
        }
    }


    fun isLogin() = sharedPreferences().contains("user_json")

    private fun sharedPreferences() =
        MainApplication.instance.getSharedPreferences("sp_db", Context.MODE_PRIVATE)
}