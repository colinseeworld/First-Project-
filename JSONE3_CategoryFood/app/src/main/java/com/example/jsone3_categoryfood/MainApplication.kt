package com.example.jsone3_categoryfood

import android.annotation.SuppressLint
import android.app.Application

class MainApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: MainApplication
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}