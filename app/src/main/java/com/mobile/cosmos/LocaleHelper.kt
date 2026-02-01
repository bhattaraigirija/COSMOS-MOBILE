package com.mobile.cosmos

import android.content.Context
import android.content.res.Configuration
import org.intellij.lang.annotations.Language
import java.util.Locale

 class LocaleHelper {
    private val PREF_NAME ="language_pref"
    private val KEY_LANG ="language"

    fun setLocale(context: Context, language: String): Context{

        saveLanguage(context,language)

        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    fun getLanguage(context: Context): String{
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getString(KEY_LANG, "en")?:"en"
    }

    fun saveLanguage(context: Context, language: String){
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().putString(KEY_LANG,language).apply()
    }
}