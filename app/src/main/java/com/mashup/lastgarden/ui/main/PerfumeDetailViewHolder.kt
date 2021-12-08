package com.mashup.lastgarden.ui.main

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.ItemPerfumeDetailBinding

class PerfumeDetailViewHolder(private val binding: ItemPerfumeDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        glideRequests: GlideRequests,
        item: PerfumeDetailItem
    ) {
        binding.perfumeCardView.setUserImage(glideRequests, item.userProfileImageUrl)
        binding.perfumeCardView.setSourceImage(glideRequests, item.imageUrl)
        binding.perfumeCardView.userName = item.userNickname
        binding.perfumeCardView.count = item.likeCount
        if (item.tags != null)
            binding.perfumeCardView.title =
                TextUtils.join(" ", item.tags.map { tag -> "#${tag.contents}" })
    }
}