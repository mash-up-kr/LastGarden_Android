package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.ItemPerfumeRecommendBinding

class PerfumeRecommendPagingAdapter(private val glideRequests: GlideRequests) :
    PagingDataAdapter<PerfumeRecommendItem, PerfumeRecommendViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PerfumeRecommendItem>() {
            override fun areItemsTheSame(
                oldItem: PerfumeRecommendItem,
                newItem: PerfumeRecommendItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PerfumeRecommendItem,
                newItem: PerfumeRecommendItem
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfumeRecommendViewHolder {
        return PerfumeRecommendViewHolder(
            ItemPerfumeRecommendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PerfumeRecommendViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(glideRequests, item)
    }
}
