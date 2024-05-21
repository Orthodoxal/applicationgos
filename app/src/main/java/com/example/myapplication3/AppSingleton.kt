package com.example.myapplication3

import android.app.Application
import androidx.room.Room
import com.example.myapplication3.db.AppDatabase

object AppSingleton {
    private lateinit var application: Application

    fun provideDatabase(): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "database.db")
            .build()

    fun init(app: App) {
        application = app
    }
}
