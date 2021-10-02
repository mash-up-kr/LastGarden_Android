package com.mashup.lastgarden.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.updateLayoutParams
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ViewPerfumeCardBinding

class PerfumeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding = ViewPerfumeCardBinding.inflate(LayoutInflater.from(context), this)

    init {
        initializeAttribute(context, attrs)
    }

    var title: String?
        get() = binding.title.toString()
        set(value) {
            binding.title.text = value
        }

    var userName: String?
        get() = binding.gradientCardView.userName
        set(value) {
            binding.gradientCardView.userName = value
        }

    var gradientColor: Int?
        get() = binding.gradientCardView.gradientColor
        set(value) {
            binding.gradientCardView.gradientColor = value
        }

    var count: Long?
        get() = binding.likeCount.text.toString().toLongOrNull()
        set(value) {
            binding.likeCount.text = value?.toString()
        }

    var isContentImageVisible: Boolean
        get() = binding.gradientCardView.isContentImageVisible
        set(value) {
            binding.gradientCardView.isContentImageVisible = value
        }

    private fun initializeAttribute(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.PerfumeCardView).use {
            title = it.getString(R.styleable.PerfumeCardView_contentTitle)
            userName = it.getString(R.styleable.PerfumeCardView_perfumeUserName)
            gradientColor = if (it.hasValue(R.styleable.PerfumeCardView_perfumeGradientColor)) {
                it.getColor(R.styleable.PerfumeCardView_perfumeGradientColor, Color.BLACK)
            } else {
                null
            }
            count = it.getInteger(R.styleable.PerfumeCardView_count, 0).toLong()
            updateGradientContentHeight(
                it.getDimensionPixelOffset(
                    R.styleable.PerfumeCardView_contentCardHeight,
                    resources.getDimensionPixelOffset(R.dimen.perfume_content_card_height)
                )
            )
            isContentImageVisible = it.getBoolean(R.styleable.PerfumeCardView_showPerfumeContent, false)
        }
    }

    fun setContentImage(glideRequests: GlideRequests, imageUrl: String? = null) {
        binding.gradientCardView.setContentImage(glideRequests, imageUrl)
    }

    fun setSourceImage(glideRequests: GlideRequests, imageUrl: String? = null) {
        binding.gradientCardView.setSourceImage(glideRequests, imageUrl)
    }

    fun setUserImage(glideRequests: GlideRequests, imageUrl: String? = null) {
        binding.gradientCardView.setUserImage(glideRequests, imageUrl)
    }

    private fun updateGradientContentHeight(height: Int) {
        binding.gradientCardView.updateLayoutParams {
            this.height = height
        }
    }
}