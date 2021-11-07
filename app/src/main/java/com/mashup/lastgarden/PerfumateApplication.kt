package com.mashup.lastgarden

import android.app.Application
import com.facebook.stetho.Stetho
import com.mashup.base.utils.DimensionToPixels
import com.mashup.lastgarden.data.PerfumeSharedPreferencesImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PerfumateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDimensionToPixels()
        initSharedPreferences()
        initStetho()
    }

    private fun initDimensionToPixels() {
        DimensionToPixels.initialize(applicationContext)
    }

    private fun initSharedPreferences() {
        PerfumeSharedPreferencesImpl.init(applicationContext)
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }
}