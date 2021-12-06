package com.mashup.lastgarden.data

interface PerfumeSharedPreferences {

    fun getIsShowOnBoarding(): Boolean
    fun saveIsShowOnBoarding(isShow: Boolean?)
    fun getAccessToken(): String?
    fun saveAccessToken(token: String?)
    fun getIsShowBanner(): Boolean
    fun saveIsShowBanner(isShow: Boolean?)
}