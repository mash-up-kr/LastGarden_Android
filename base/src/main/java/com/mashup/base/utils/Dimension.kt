package com.mashup.base.utils

import androidx.annotation.Px

val Number.dp: Int @Px get() = DimensionToPixels.dpAsInt(this)

val Number.dpAsFloat: Float @Px get() = DimensionToPixels.dpAsFloat(this)

val Number.sp: Int @Px get() = DimensionToPixels.spAsInt(this)

val Number.spAsFloat: Float @Px get() = DimensionToPixels.spAsFloat(this)
