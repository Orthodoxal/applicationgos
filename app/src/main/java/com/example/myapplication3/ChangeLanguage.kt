package com.example.myapplication3

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

object LanguageHelper {
    private var languageCode: String = "ru"

    fun AppCompatActivity.changeLanguage() {
        languageCode = when (languageCode) {
            "ru" -> "en"
            else -> "ru"
        }
        recreate()
    }

    fun setLanguage(context: Context): ContextWrapper {
        val configuration = context.resources.configuration
        val newLocale = Locale(languageCode)
        configuration.setLocale(newLocale)
        return ContextWrapper(context.createConfigurationContext(configuration))
    }
}
