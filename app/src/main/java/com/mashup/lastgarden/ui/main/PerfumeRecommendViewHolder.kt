package com.mashup.lastgarden.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.customview.PerfumeView
import com.mashup.lastgarden.databinding.ItemPerfumeRecommendBinding

class PerfumeRecommendViewHolder(
    binding: ItemPerfumeRecommendBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        glideRequests: GlideRequests,
        item: MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem,
        listener: MainAdapter.OnMainItemClickListener?
    ) {
        if (itemView !is PerfumeView) return

        itemView.apply {
            brand = item.brandName
            name = item.name
            count = item.likeCount
            setImageUrl(glideRequests, item.imageUrl)
            setOnClickListener { listener?.onPerfumeRecommendClick(item.id) }
        }
    }
}