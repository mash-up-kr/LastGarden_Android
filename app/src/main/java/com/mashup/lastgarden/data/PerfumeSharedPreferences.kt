package com.mashup.lastgarden.data

interface PerfumeSharedPreferences {

    fun getAccessToken(): String?
    fun saveAccessToken(token: String?)
    fun getIsShowBanner(): Boolean
    fun saveIsShowBanner(isShow: Boolean?)
}