package com.mashup.lastgarden.ui.upload.perfume

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.customview.PerfumeView
import com.mashup.lastgarden.databinding.ItemPerfumeRecommendBinding

typealias PerfumeSelectedItem = PerfumeItem.PerfumeSearchedItem

interface OnPerfumeClickListener {
    fun onPerfumeClick(perfume: PerfumeSelectedItem)
}

enum class PerfumePayload {
    SelectedPayload, LikePayload
}

class PerfumeSelectAdapter(
    private val glideRequests: GlideRequests,
    private val onPerfumeClickListener: OnPerfumeClickListener
) : PagingDataAdapter<PerfumeSelectedItem, PerfumeSelectAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PerfumeSelectedItem>() {
            override fun areItemsTheSame(
                oldItem: PerfumeSelectedItem,
                newItem: PerfumeSelectedItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PerfumeSelectedItem,
                newItem: PerfumeSelectedItem
            ): Boolean = oldItem == newItem

            override fun getChangePayload(
                oldItem: PerfumeSelectedItem,
                newItem: PerfumeSelectedItem
            ): Any? {
                return when {
                    oldItem.isSelected != newItem.isSelected -> PerfumePayload.SelectedPayload
                    oldItem.likeCount != newItem.likeCount -> PerfumePayload.LikePayload
                    else -> null
                }
            }
        }
    }

    class ViewHolder(binding: ItemPerfumeRecommendBinding) : RecyclerView.ViewHolder(binding.root)

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
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val item = getItem(position) ?: return
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.onEach { payload ->
                when (payload) {
                    PerfumePayload.SelectedPayload -> {
                        holder.bindPerfumeSelected(item.isSelected)
                    }
                    PerfumePayload.LikePayload -> {
                        holder.bindLikeSelected(item.likeCount)
                    }
                }
            }
        }
    }

    private fun ViewHolder.bind(item: PerfumeSelectedItem) {
        if (itemView !is PerfumeView) return

        itemView.apply {
            brand = item.brandName
            name = item.name
            count = item.likeCount
            isSelected = item.isSelected
            setImageUrl(glideRequests, item.imageUrl)
            setOnClickListener {
                onPerfumeClickListener.onPerfumeClick(item)
            }
        }
    }

    private fun ViewHolder.bindPerfumeSelected(isSelected: Boolean) {
        if (itemView !is PerfumeView) return

        itemView.apply {
            this.isSelected = isSelected
        }
    }

    private fun ViewHolder.bindLikeSelected(likeCount: Long) {
        if (itemView !is PerfumeView) return

        itemView.apply {
            count = likeCount
        }
    }
}