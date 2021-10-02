package com.mashup.base.image

import androidx.annotation.ColorInt
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.BaseRequestOptions
import com.mashup.base.image.transformation.RoundedTransformation

@GlideExtension
object PerfumeGlideExtension {

    @GlideOption
    @JvmStatic
    fun userImage(
        options: BaseRequestOptions<*>,
        @ColorInt color: Int?
    ): BaseRequestOptions<*> = options.transform(
        CircleCrop(),
        RoundedTransformation(
            isOval = true,
            borderWidthDp = 1f,
            borderColor = color
        )
    )
}