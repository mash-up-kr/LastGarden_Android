package com.mashup.base.image

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.widget.ImageView
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class RoundedDrawable private constructor(bitmap: Bitmap) : Drawable() {

    companion object {
        const val DEFAULT_BORDER_COLOR = Color.BLACK

        fun fromBitmap(bitmap: Bitmap): RoundedDrawable = RoundedDrawable(bitmap)
    }

    private val config = bitmap.config ?: Bitmap.Config.ARGB_8888
    private var roundPath: Path? = null

    private val bounds = RectF()
    private val drawableRect = RectF()
    private val bitmapRect = RectF()
    private var bitmapShader: BitmapShader
    private var bitmapPaint: Paint
    private var bitmapWidth: Int = bitmap.width
    private var bitmapHeight: Int = bitmap.height
    private val borderRect = RectF()
    private var borderPaint: Paint
    private val shaderMatrix = Matrix()

    var isOval: Boolean = false

    var drawablePadding: Float = 0f

    var borderWidth: Float = 0f
        set(value) {
            field = value
            borderPaint.strokeWidth = value
        }

    var borderColor: ColorStateList = ColorStateList.valueOf(DEFAULT_BORDER_COLOR)
        set(value) {
            field = value
            borderPaint.color = value.getColorForState(state, DEFAULT_BORDER_COLOR)
        }

    var scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_CENTER

    var fillColor: Int = Color.WHITE

    var cornerRadius: Float = 0f

    var cornerType: RoundedCornersTransformation.CornerType? = null

    init {
        bitmapRect.set(0f, 0f, bitmapWidth.toFloat(), bitmapHeight.toFloat())

        bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapShader.setLocalMatrix(shaderMatrix)

        bitmapPaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            shader = bitmapShader
        }

        borderPaint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = borderColor.getColorForState(state, DEFAULT_BORDER_COLOR)
            strokeWidth = borderWidth
        }
    }

    override fun isStateful(): Boolean = borderColor.isStateful

    override fun onStateChange(state: IntArray?): Boolean {
        val newColor = borderColor.getColorForState(state, 0)
        return if (borderPaint.color != newColor) {
            borderPaint.color = newColor
            true
        } else {
            super.onStateChange(state)
        }
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        bounds?.let { this.bounds.set(it) }
        updateShaderMatrix()
    }

    override fun draw(canvas: Canvas) {
        if (config == Bitmap.Config.RGB_565) {
            canvas.drawColor(fillColor)
        }
        if (isOval) {
            if (borderWidth > 0) {
                canvas.drawOval(drawableRect, bitmapPaint)
                canvas.drawOval(borderRect, borderPaint)
            } else {
                canvas.drawOval(drawableRect, bitmapPaint)
            }
        } else {
            if (borderWidth > 0) {
                buildRoundPath()
                if (isIndividualRoundedCorner()) {
                    roundPath?.let {
                        canvas.drawPath(it, bitmapPaint)
                        canvas.drawPath(it, borderPaint)
                    }
                } else {
                    canvas.drawRoundRect(
                        drawableRect,
                        cornerRadius.coerceAtLeast(0f),
                        cornerRadius.coerceAtLeast(0f),
                        bitmapPaint
                    )
                    canvas.drawRoundRect(borderRect, cornerRadius, cornerRadius, borderPaint)
                }
            } else {
                buildRoundPath()
                if (isIndividualRoundedCorner()) {
                    roundPath?.let {
                        canvas.drawPath(it, bitmapPaint)
                    }
                } else {
                    canvas.drawRoundRect(drawableRect, cornerRadius, cornerRadius, bitmapPaint)
                }
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        bitmapPaint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        bitmapPaint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun setFilterBitmap(filter: Boolean) {
        bitmapPaint.isFilterBitmap = filter
        invalidateSelf()
    }

    override fun getIntrinsicWidth(): Int = bitmapWidth

    override fun getIntrinsicHeight(): Int = bitmapHeight

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    fun toBitmap(recycled: Bitmap): Bitmap? = drawableToBitmap(recycled)

    private fun drawableToBitmap(recycled: Bitmap): Bitmap? {
        val width = intrinsicWidth.coerceAtLeast(2)
        val height = intrinsicHeight.coerceAtLeast(2)
        return try {
            val result =
                if (recycled.width == width && recycled.height == height) {
                    recycled
                } else {
                    Bitmap.createBitmap(width, height, config)
                }
            val canvas = Canvas(result)
            setBounds(0, 0, width, height)
            draw(canvas)
            result
        } catch (e: Exception) {
            null
        }
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx: Float
        var dy: Float

        when (scaleType) {
            ImageView.ScaleType.CENTER -> {
                borderRect.set(bounds)
                borderRect.inset(borderWidth / 2f, borderWidth / 2f)

                shaderMatrix.reset()
                shaderMatrix.setTranslate(
                    (borderRect.width() - bitmapWidth) * 0.5f + 0.5f,
                    (borderRect.height() - bitmapHeight) * 0.5f + 0.5f
                )
            }
            ImageView.ScaleType.CENTER_CROP -> {
                borderRect.set(bounds)
                borderRect.inset(borderWidth / 2f, borderWidth / 2f)
                shaderMatrix.reset()

                dx = 0f
                dy = 0f

                if (bitmapWidth * borderRect.height() > borderRect.width() * bitmapHeight) {
                    scale = borderRect.height() / bitmapHeight
                    dx = (borderRect.width() - bitmapWidth * scale) * 0.5f
                } else {
                    scale = borderRect.width() / bitmapWidth
                    dy = (borderRect.height() - bitmapHeight * scale) * 0.5f
                }

                shaderMatrix.setScale(scale, scale)
                shaderMatrix.postTranslate(dx + 0.5f + borderWidth, dy + 0.5f + borderWidth)
            }
            ImageView.ScaleType.FIT_XY -> {
                borderRect.set(bounds)
                borderRect.inset(borderWidth / 2f, borderWidth / 2f)
                shaderMatrix.reset()
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
            ImageView.ScaleType.FIT_START -> {
                borderRect.set(bitmapRect)
                shaderMatrix.setRectToRect(bitmapRect, bounds, Matrix.ScaleToFit.START)
                shaderMatrix.mapRect(borderRect)
                borderRect.inset(borderWidth / 2f, borderWidth / 2f)
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
            ImageView.ScaleType.FIT_END -> {
                borderRect.set(bitmapRect)
                shaderMatrix.setRectToRect(bitmapRect, bounds, Matrix.ScaleToFit.END)
                shaderMatrix.mapRect(borderRect)
                borderRect.inset(borderWidth / 2f, borderWidth / 2f)
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
            else -> {
                borderRect.set(bitmapRect)
                shaderMatrix.setRectToRect(bitmapRect, bounds, Matrix.ScaleToFit.CENTER)
                shaderMatrix.mapRect(borderRect)
                borderRect.inset(borderWidth / 2f, borderWidth / 2f)
                shaderMatrix.setRectToRect(bitmapRect, borderRect, Matrix.ScaleToFit.FILL)
            }
        }

        drawableRect.set(
            borderRect.left + drawablePadding,
            borderRect.top + drawablePadding,
            borderRect.right - drawablePadding,
            borderRect.bottom - drawablePadding
        )
        bitmapShader.setLocalMatrix(shaderMatrix)
    }

    private fun isIndividualRoundedCorner(): Boolean {
        return roundPath != null && cornerType != null && RoundedCornersTransformation.CornerType.ALL == cornerType
    }

    private fun buildRoundPath() {
        when (cornerType ?: return) {
            RoundedCornersTransformation.CornerType.TOP -> buildTopRoundPath()
            RoundedCornersTransformation.CornerType.BOTTOM -> buildBottomRoundPath()
            RoundedCornersTransformation.CornerType.LEFT -> buildLeftRoundPath()
            RoundedCornersTransformation.CornerType.RIGHT -> buildRightRoundPath()
            RoundedCornersTransformation.CornerType.TOP_LEFT -> buildTopLeftRoundPath()
            RoundedCornersTransformation.CornerType.TOP_RIGHT -> buildTopRightRoundPath()
            RoundedCornersTransformation.CornerType.BOTTOM_LEFT -> buildBottomLeftRoundPath()
            RoundedCornersTransformation.CornerType.BOTTOM_RIGHT -> buildBottomRightRoundPath()
            else -> {
                // Do Nothing
            }
        }
    }

    private fun buildTopRoundPath() {
        roundPath = Path().apply {
            moveTo(drawableRect.left + cornerRadius, drawableRect.top)
            lineTo(drawableRect.right - cornerRadius, drawableRect.top)
            quadTo(
                drawableRect.right,
                drawableRect.top,
                drawableRect.right,
                drawableRect.top + cornerRadius
            )
            lineTo(drawableRect.right, drawableRect.bottom)
            lineTo(drawableRect.left, drawableRect.bottom)
            lineTo(drawableRect.left, drawableRect.top + cornerRadius)
            quadTo(
                drawableRect.left,
                drawableRect.top,
                drawableRect.left + cornerRadius,
                drawableRect.top
            )
        }
    }

    private fun buildBottomRoundPath() {
        roundPath = Path().apply {
            moveTo(drawableRect.left, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.bottom - cornerRadius)
            quadTo(
                drawableRect.right,
                drawableRect.bottom,
                drawableRect.right - cornerRadius,
                drawableRect.bottom
            )
            lineTo(drawableRect.left + cornerRadius, drawableRect.bottom)
            quadTo(
                drawableRect.left,
                drawableRect.bottom,
                drawableRect.left,
                drawableRect.bottom - cornerRadius
            )
            lineTo(drawableRect.left, drawableRect.top)
        }
    }

    private fun buildLeftRoundPath() {
        roundPath = Path().apply {
            moveTo(drawableRect.left + cornerRadius, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.bottom)
            lineTo(drawableRect.left + cornerRadius, drawableRect.bottom)
            quadTo(
                drawableRect.left,
                drawableRect.bottom,
                drawableRect.left,
                drawableRect.bottom - cornerRadius
            )
            lineTo(drawableRect.left, drawableRect.top + cornerRadius)
            quadTo(
                drawableRect.left,
                drawableRect.top,
                drawableRect.left + cornerRadius,
                drawableRect.top
            )
        }
    }

    private fun buildRightRoundPath() {
        roundPath = Path().apply {
            moveTo(drawableRect.left, drawableRect.top)
            lineTo(drawableRect.right - cornerRadius, drawableRect.top)
            quadTo(
                drawableRect.right,
                drawableRect.top,
                drawableRect.right,
                drawableRect.right + cornerRadius
            )
            lineTo(drawableRect.right, drawableRect.bottom - cornerRadius)
            quadTo(
                drawableRect.right,
                drawableRect.bottom,
                drawableRect.right - cornerRadius,
                drawableRect.bottom
            )
            lineTo(drawableRect.left, drawableRect.bottom)
            lineTo(drawableRect.left, drawableRect.top)
        }
    }

    private fun buildTopLeftRoundPath() {
        roundPath = Path().apply {
            moveTo(drawableRect.left + cornerRadius, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.bottom)
            lineTo(drawableRect.left, drawableRect.bottom)
            lineTo(drawableRect.left, drawableRect.top + cornerRadius)
            quadTo(
                drawableRect.left,
                drawableRect.top,
                drawableRect.left + cornerRadius,
                drawableRect.top
            )
        }
    }

    private fun buildTopRightRoundPath() {
        roundPath = Path().apply {
            moveTo(drawableRect.left, drawableRect.top)
            lineTo(drawableRect.right - cornerRadius, drawableRect.top)
            quadTo(
                drawableRect.right,
                drawableRect.top,
                drawableRect.right,
                drawableRect.top + cornerRadius
            )
            lineTo(drawableRect.right, drawableRect.bottom)
            lineTo(drawableRect.left, drawableRect.bottom)
            lineTo(drawableRect.left, drawableRect.top)
        }
    }

    private fun buildBottomLeftRoundPath() {
        roundPath = Path().apply {
            moveTo(drawableRect.left, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.bottom)
            lineTo(drawableRect.left + cornerRadius, drawableRect.bottom)
            quadTo(
                drawableRect.left,
                drawableRect.bottom,
                drawableRect.left,
                drawableRect.bottom - cornerRadius
            )
            lineTo(drawableRect.left, drawableRect.top)
        }
    }

    private fun buildBottomRightRoundPath() {
        roundPath = Path().apply {
            moveTo(drawableRect.left, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.top)
            lineTo(drawableRect.right, drawableRect.bottom - cornerRadius)
            quadTo(
                drawableRect.right,
                drawableRect.bottom,
                drawableRect.right - cornerRadius,
                drawableRect.bottom
            )
            lineTo(drawableRect.left, drawableRect.bottom)
            lineTo(drawableRect.left, drawableRect.top)
        }
    }
}