package com.mashup.lastgarden.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ViewPerfumeBinding

class PerfumeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding: ViewPerfumeBinding =
        ViewPerfumeBinding.inflate(LayoutInflater.from(context), this)

    private var currentImageUrl: String? = null

    var brand: String?
        get() = binding.perfumeBrand.toString()
        set(value) {
            binding.perfumeBrand.text = value
        }

    var name: String?
        get() = binding.perfumeName.toString()
        set(value) {
            binding.perfumeName.text = value
        }

    var count: Long?
        get() = binding.perfumeCount.toString().toLongOrNull()
        set(value) {
            binding.perfumeCount.text = value?.toString()
        }

    var isPerfumeSelected: Boolean = false
        get() = binding.selectedImage.isVisible
        set(value) {
            field = value
            binding.selectedImage.isVisible = value
            binding.perfumeImageContainer.setBackgroundResource(
                if (value) {
                    R.drawable.bg_selected_perfume_on_list
                } else {
                    R.drawable.card_radius_background_shape
                }
            )
        }

    fun setImageUrl(glideRequests: GlideRequests, imageUrl: String?) {
        if (imageUrl != null && currentImageUrl == imageUrl) return
        glideRequests.load(imageUrl)
            .placeholder(R.drawable.ic_empty_perfume)
            .error(R.drawable.ic_empty_perfume)
            .centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    currentImageUrl = null
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    currentImageUrl = imageUrl
                    return false
                }
            })
            .into(binding.perfumeImage)
    }
}