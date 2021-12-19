package com.mashup.lastgarden.customview.loadingview

import android.animation.ValueAnimator
import android.support.annotation.Px
import android.view.animation.Interpolator
import java.lang.Math.sin

internal class LoadingIndicatorHelper(
    count: Int = DEFAULT_INDICATOR_COUNT,
    duration: Long = DEFAULT_DURATION,
    delayBetweenIndicators: Long = DEFAULT_DELAY,
    onUpdate: () -> Unit
) {

    private val animator get() = animators[0]

    private val animators: List<ValueAnimator> = (0 until count)
        .map { createValueAnimator(it, duration, delayBetweenIndicators, onUpdate) }

    private fun createValueAnimator(
        index: Int,
        duration: Long = DEFAULT_DURATION,
        delayBetweenIndicators: Long = DEFAULT_DELAY,
        onUpdate: () -> Unit
    ): ValueAnimator =
        ValueAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            interpolator = SineInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            if (index == 0) {
                addUpdateListener {
                    animators.forEachIndexed { index, valueAnimator ->
                        if (index != 0) {
                            valueAnimator.currentPlayTime =
                                currentPlayTime - delayBetweenIndicators * index
                        }
                    }
                    onUpdate()
                }
            }
        }

    fun start() {
        animator.start()
    }

    fun end() {
        animator.end()
    }

    val isRunning: Boolean get() = animator.isRunning

    fun startIfNotRunning() {
        if (!isRunning) {
            start()
        }
    }

    fun draw(
        @Px width: Int,
        @Px height: Int,
        @Px indicatorRadius: Float,
        @Px indicatorPadding: Float,
        block: (Float, Float) -> Unit
    ) {
        animators.forEachIndexed { index, animator ->
            val (cx, cy) = computeCenterOfCircle(
                animator = animator,
                index = index,
                measuredWidth = width,
                measuredHeight = height,
                radius = indicatorRadius,
                indicatorPadding = indicatorPadding
            )
            block(cx, cy)
        }
    }

    private class SineInterpolator : Interpolator {
        override fun getInterpolation(input: Float): Float =
            sin(input.toDouble() * Math.PI * 2.0).toFloat()
    }

    companion object {
        const val DEFAULT_INDICATOR_COUNT = 3
        const val DEFAULT_DURATION = 530L
        const val DEFAULT_DELAY = 70L

        fun computeCenterOfCircle(
            animator: ValueAnimator,
            index: Int,
            measuredWidth: Int,
            measuredHeight: Int,
            radius: Float,
            indicatorPadding: Float
        ): Pair<Float, Float> {
            val width = measuredWidth.toFloat()
            val height = measuredHeight.toFloat()
            val interpolation: Float = animator.animatedValue as Float
            val padding: Float = indicatorPadding + radius * 2
            val cx: Float = (width / 2) - padding + padding * index
            val cy: Float = (height / 2) + (interpolation * radius)
            return cx to cy
        }
    }
}
