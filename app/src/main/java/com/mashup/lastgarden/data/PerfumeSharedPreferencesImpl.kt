package com.mashup.lastgarden.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object PerfumeSharedPreferencesImpl : PerfumeSharedPreferences {
    private const val PREFERENCES_NAME = "perfume_preferences"
    private lateinit var sharedPreferences: SharedPreferences

    private const val KEY_ACCESS_TOKEN = "key_access_token"

    fun init(applicationContext: Context) {
        sharedPreferences =
            applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun getAccessToken(): String? = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    override fun saveAccessToken(token: String?) {
        sharedPreferences.edit {
            if (token == null) {
                remove(KEY_ACCESS_TOKEN)
            } else {
                putString(KEY_ACCESS_TOKEN, token)
            }
        }
    }
}