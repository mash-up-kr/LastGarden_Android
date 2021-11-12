package com.mashup.lastgarden.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object PerfumeSharedPreferencesImpl : PerfumeSharedPreferences {
    private const val PREFERENCES_NAME = "perfume_preferences"
    private lateinit var sharedPreferences: SharedPreferences

    private const val KEY_ACCESS_TOKEN = "key_access_token"
    private const val KEY_IS_SHOW_BANNER = "key_is_show_banner"
    private const val KEY_IS_SHOW_ON_BOARDING = "key_is_onBoarding"

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

    override fun getIsShowOnBoarding(): Boolean =
        sharedPreferences.getBoolean(KEY_IS_SHOW_ON_BOARDING, true)

    override fun saveIsShowOnBoarding(isShow: Boolean?) {
        sharedPreferences.edit {
            if (isShow == null) {
                remove(KEY_IS_SHOW_ON_BOARDING)
            } else {
                putBoolean(KEY_IS_SHOW_ON_BOARDING, isShow)
            }
        }
    }

    override fun getIsShowBanner(): Boolean =
        sharedPreferences.getBoolean(KEY_IS_SHOW_BANNER, true)

    override fun saveIsShowBanner(isShow: Boolean?) {
        sharedPreferences.edit {
            if (isShow == null) {
                remove(KEY_IS_SHOW_BANNER)
            } else {
                putBoolean(KEY_IS_SHOW_BANNER, isShow)
            }
        }
    }
}