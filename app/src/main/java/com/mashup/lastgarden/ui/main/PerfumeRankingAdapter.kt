package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.customview.PerfumeRankingView
import com.mashup.lastgarden.databinding.ItemPerfumeRankingBinding

private typealias PerfumeRankingItem = MainAdapterItem.PerfumeRankings.PerfumeRankingItem

class PerfumeRankingAdapter(
    private val glideRequests: GlideRequests
) : ListAdapter<PerfumeRankingItem, PerfumeRankingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PerfumeRankingItem>() {
            override fun areItemsTheSame(
                oldItem: PerfumeRankingItem,
                newItem: PerfumeRankingItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PerfumeRankingItem,
                newItem: PerfumeRankingItem
            ): Boolean = oldItem == newItem
        }
    }

    class ViewHolder(binding: ItemPerfumeRankingBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPerfumeRankingBinding.inflate(
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

    private fun ViewHolder.bind(item: PerfumeRankingItem) {
        if (itemView !is PerfumeRankingView) return

        itemView.apply {
            ranking = item.rank
            brand = item.brandName
            name = item.name
            setImageUrl(glideRequests, item.imageUrl)
        }
    }
}