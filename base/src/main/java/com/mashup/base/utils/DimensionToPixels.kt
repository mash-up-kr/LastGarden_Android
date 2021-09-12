package com.mashup.base.utils

import android.content.Context
import android.util.TypedValue
import androidx.annotation.Px

object DimensionToPixels {

    private fun Context.toPixels(value: Number, unit: Int = TypedValue.COMPLEX_UNIT_DIP): Int =
        TypedValue.applyDimension(unit, value.toFloat(), resources.displayMetrics).toInt()

    private fun Context.toPixelsAsFloat(
        value: Number,
        unit: Int = TypedValue.COMPLEX_UNIT_DIP
    ): Float = TypedValue.applyDimension(unit, value.toFloat(), resources.displayMetrics)

    private fun Number.dpToPixels(context: Context): Int = context.toPixels(this)

    private fun Number.dpToPixelsAsFloat(context: Context): Float = context.toPixelsAsFloat(this)

    private lateinit var applicationContext: Context

    fun initialize(applicationContext: Context) {
        this.applicationContext = applicationContext
    }

    @Px
    fun dpAsInt(dp: Number): Int = DpToPixels.asInt(applicationContext, dp)

    @Px
    fun dpAsFloat(dp: Number): Float = DpToPixels.asFloat(applicationContext, dp)

    @Px
    fun spAsInt(sp: Number): Int = SpToPixels.asInt(applicationContext, sp)

    @Px
    fun spAsFloat(sp: Number): Float = SpToPixels.asFloat(applicationContext, sp)

    object DpToPixels {
        private val intCache = mutableMapOf<Number, Int>()
        private val floatCache = mutableMapOf<Number, Float>()

        @Px
        fun asInt(context: Context, dp: Number): Int =
            intCache.getOrPut(dp) { dp.dpToPixels(context) }

        @Px
        fun asFloat(context: Context, dp: Number): Float =
            floatCache.getOrPut(dp) { dp.dpToPixelsAsFloat(context) }
    }

    object SpToPixels {
        private val intCache = mutableMapOf<Number, Int>()
        private val floatCache = mutableMapOf<Number, Float>()

        @Px
        fun asInt(context: Context, dp: Number): Int =
            intCache.getOrPut(dp) { dp.dpToPixels(context) }

        @Px
        fun asFloat(context: Context, dp: Number): Float =
            floatCache.getOrPut(dp) { dp.dpToPixelsAsFloat(context) }
    }
}
