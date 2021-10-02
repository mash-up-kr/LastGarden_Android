package com.mashup.base.image.transformation

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.mashup.base.image.RoundedDrawable
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.nio.charset.Charset
import java.security.MessageDigest

class RoundedTransformation(
    cornerRadiusDp: Float = 0f,
    borderWidthDp: Float = 0f,
    private val cacheId: String? = null,
    private val isOval: Boolean = false,
    private val fillColor: Int = Color.WHITE,
    private val cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
    @ColorInt private val borderColor: Int? = RoundedDrawable.DEFAULT_BORDER_COLOR,
    private val scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_CENTER
) : BitmapTransformation() {

    private val displayMetrics = Resources.getSystem().displayMetrics

    @Px
    private val cornerRadiusPx =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDp, displayMetrics)

    @Px
    private val borderWidthPx =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, borderWidthDp, displayMetrics)

    companion object {
        private const val ID = "com.mashup.base.image.transformation.RoundedTransformation"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap? {
        val toReuse = pool.get(outWidth, outHeight, toTransform.config ?: Bitmap.Config.ARGB_8888)
        val ratio = (outWidth / toTransform.width).coerceAtMost(outHeight / toTransform.height)
        val transformed = RoundedDrawable.fromBitmap(toTransform).apply {
            fillColor = this@RoundedTransformation.fillColor
            cornerRadius = this@RoundedTransformation.cornerRadiusPx / ratio
            cornerType = this@RoundedTransformation.cornerType
            scaleType = this@RoundedTransformation.scaleType
            borderWidth = borderWidthPx
            drawablePadding = borderWidthPx
            this@RoundedTransformation.borderColor?.let {
                borderColor = ColorStateList.valueOf(it)
            }
            isOval = this@RoundedTransformation.isOval
        }.toBitmap(toReuse)

        if (toReuse != transformed) {
            pool.put(toReuse)
        }

        return transformed
    }

    override fun hashCode(): Int = ID.hashCode()

    override fun equals(other: Any?): Boolean {
        if (cacheId?.isEmpty() == true) return false
        if (other == null) return false
        if (other !is RoundedTransformation) return false
        if ((other as? RoundedTransformation)?.cacheId?.isEmpty() == true) return false
        return cacheId?.equals((other as? RoundedTransformation)?.cacheId) ?: false
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }
}