package com.mashup.lastgarden

import android.app.Application
import com.mashup.base.utils.DimensionToPixels
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PerfumateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDimensionToPixels()
    }

    private fun initDimensionToPixels() {
        DimensionToPixels.initialize(applicationContext)
    }
}