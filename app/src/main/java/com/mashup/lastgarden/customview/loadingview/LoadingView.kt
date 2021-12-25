package com.mashup.lastgarden.customview.loadingview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.mashup.lastgarden.R
import kotlin.math.min

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.loadingViewStyle,
    defStyleRes: Int = R.style.Widget_SeeHyangComponents_LoadingView
) : View(context, attrs, defStyleAttr) {

    private val loadingIndicatorPaint: Paint = Paint()

    @ColorInt
    private val indicatorDefaultColor: Int = context.getColor(R.color.white)

    private val loadingIndicatorHelper = LoadingIndicatorHelper { invalidate() }

    @Px
    var indicatorRadius: Int =
        context.resources.getDimensionPixelSize(R.dimen.loading_view_indicator_radius_default)
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    @Px
    var indicatorPadding: Int =
        context.resources.getDimensionPixelSize(R.dimen.loading_view_indicator_padding_default)
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    var indicatorTint: ColorStateList? = null
        set(value) {
            field = value
            invalidate()
        }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingView,
            defStyleAttr,
            defStyleRes
        )
        indicatorRadius = a.getDimensionPixelSize(
            R.styleable.LoadingView_indicatorRadius,
            resources.getDimensionPixelSize(R.dimen.loading_view_indicator_radius_default)
        )
        indicatorPadding = a.getDimensionPixelSize(
            R.styleable.LoadingView_indicatorPadding,
            resources.getDimensionPixelSize(R.dimen.loading_view_indicator_padding_default)
        )
        indicatorTint = a.getColorStateList(R.styleable.LoadingView_indicatorTint)
        a.recycle()

        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getSize(suggestedMinimumWidth, widthMeasureSpec),
            getSize(suggestedMinimumHeight, heightMeasureSpec)
        )
    }

    private fun getSize(size: Int, measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return when (specMode) {
            MeasureSpec.AT_MOST -> min(size, specSize)
            MeasureSpec.EXACTLY -> specSize
            else -> size
        }
    }

    override fun getSuggestedMinimumWidth(): Int {
        val widthOfThreeIndicator =
            indicatorRadius * 2 * LoadingIndicatorHelper.DEFAULT_INDICATOR_COUNT
        val innerPadding = indicatorPadding * (LoadingIndicatorHelper.DEFAULT_INDICATOR_COUNT - 1)
        return widthOfThreeIndicator + innerPadding + paddingStart + paddingEnd
    }

    override fun getSuggestedMinimumHeight(): Int {
        // Sine 함수 상에서 indicator radius를 고려한 최대 진폭
        val animatedHeight = indicatorRadius * 4
        return animatedHeight + paddingTop + paddingBottom
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (alpha <= 0 || canvas == null) {
            loadingIndicatorHelper.end()
            return
        }

        loadingIndicatorHelper.startIfNotRunning()

        loadingIndicatorPaint.color =
            indicatorTint?.getColorForState(drawableState, indicatorDefaultColor)
                ?: indicatorDefaultColor

        val radius = indicatorRadius.toFloat()
        val padding = indicatorPadding.toFloat()
        loadingIndicatorHelper.draw(
            width = measuredWidth,
            height = measuredHeight,
            indicatorRadius = radius,
            indicatorPadding = padding
        ) { cx, cy -> canvas.drawCircle(cx, cy, radius, loadingIndicatorPaint) }
    }
}