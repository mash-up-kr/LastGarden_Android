package com.mashup.lastgarden

import android.app.Application
import com.mashup.base.utils.DimensionToPixels

class PerfumateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDimensionToPixels()
    }

    private fun initDimensionToPixels() {
        DimensionToPixels.initialize(applicationContext)
    }
}