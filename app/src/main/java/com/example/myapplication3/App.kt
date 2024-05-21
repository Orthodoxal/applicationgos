package com.example.myapplication3

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppSingleton.init(this)
    }
}
