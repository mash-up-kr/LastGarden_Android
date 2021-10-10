package com.mashup.lastgarden.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.ViewPerfumeRankingBinding

class PerfumeRankingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ViewPerfumeRankingBinding.inflate(LayoutInflater.from(context), this)

    private var currentImageUrl: String? = null

    var ranking: Int?
        get() = binding.ranking.text.toString().toIntOrNull()
        set(value) {
            binding.ranking.text = value.toString()
        }

    var brand: String?
        get() = binding.brand.text.toString()
        set(value) {
            binding.brand.text = value
        }

    var name: String?
        get() = binding.name.text.toString()
        set(value) {
            binding.name.text = value
        }

    fun setImageUrl(glideRequests: GlideRequests, imageUrl: String?) {
        if (imageUrl != null && currentImageUrl == imageUrl) return
        glideRequests.load(imageUrl)
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