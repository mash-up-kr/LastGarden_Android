package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.customview.PerfumeView
import com.mashup.lastgarden.databinding.ItemPerfumeRecommendBinding

private typealias PerfumeRecommendItem = MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem

class PerfumeRecommendAdapter(
    private val glideRequests: GlideRequests
) : ListAdapter<PerfumeRecommendItem, PerfumeRecommendAdapter.ViewHolder>(DIFF_CALLBACK) {

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

    class ViewHolder(binding: ItemPerfumeRecommendBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPerfumeRecommendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position !in 0 until itemCount) return
        holder.bind(getItem(position))
    }

    private fun ViewHolder.bind(item: PerfumeRecommendItem) {
        if (itemView !is PerfumeView) return

        itemView.apply {
            brand = item.brandName
            name = item.name
            count = item.likeCount
            setImageUrl(glideRequests, item.imageUrl)
        }
    }
}