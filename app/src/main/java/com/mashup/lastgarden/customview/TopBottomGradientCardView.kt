package com.mashup.lastgarden.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mashup.base.image.GlideRequests
import com.mashup.base.utils.dp
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ViewBottomGradientCardBinding

class TopBottomGradientCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding: ViewBottomGradientCardBinding =
        ViewBottomGradientCardBinding.inflate(LayoutInflater.from(context), this)

    init {
        initializeAttributes(context, attrs)
    }

    private var currentContentImageUrl: String? = null
    private var currentSourceImageUrl: String? = null

    var userName: String?
        get() = binding.userName.toString()
        set(value) {
            binding.userName.text = value
        }

    var isContentImageVisible: Boolean
        get() = binding.contentImage.isVisible
        set(value) {
            binding.contentImage.isVisible = value
        }

    private fun initializeAttributes(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.TopBottomGradientCardView).use {
            userName = it.getString(R.styleable.TopBottomGradientCardView_userName)
            isContentImageVisible =
                it.getBoolean(R.styleable.TopBottomGradientCardView_showContent, false)
        }
    }

    fun setUserImage(glideRequests: GlideRequests, userImageUrl: String?) {
        binding.userImage.setImageUrl(glideRequests, userImageUrl)
    }

    fun setContentImage(glideRequests: GlideRequests, imageUrl: String?) {
        if (imageUrl != null && currentContentImageUrl == imageUrl) return

        glideRequests.load(imageUrl)
            .circleCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    currentContentImageUrl = null
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    currentContentImageUrl = imageUrl
                    return false
                }

            })
            .into(binding.contentImage)
    }

    fun setSourceImage(
        glideRequests: GlideRequests,
        imageUrl: String?,
        errorDrawableId: Int? = null,
    ) {
        if (imageUrl != null && currentSourceImageUrl == imageUrl) return

        glideRequests.load(imageUrl)
            .placeholder(errorDrawableId ?: R.drawable.ic_story_empty_horizontal)
            .error(errorDrawableId ?: R.drawable.ic_story_empty_horizontal)
            .transform(CenterCrop(), RoundedCorners(8.dp))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    currentSourceImageUrl = null
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    currentSourceImageUrl = imageUrl
                    return false
                }
            })
            .into(binding.sourceImage)
    }
}