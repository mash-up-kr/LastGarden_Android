package com.mashup.lastgarden

import android.app.Application
import com.mashup.base.utils.DimensionToPixels
import com.mashup.lastgarden.data.PerfumeSharedPreferencesImpl
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PerfumateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDimensionToPixels()
        initSharedPreferences()
    }

    private fun initDimensionToPixels() {
        DimensionToPixels.initialize(applicationContext)
    }

    private fun initSharedPreferences() {
        PerfumeSharedPreferencesImpl.init(applicationContext)
    }
}