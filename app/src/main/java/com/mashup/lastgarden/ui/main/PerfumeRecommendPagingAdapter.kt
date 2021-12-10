package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.customview.PerfumeView
import com.mashup.lastgarden.databinding.ItemPerfumeRecommendBinding

class PerfumeRecommendPagingAdapter(
    private val glideRequests: GlideRequests,
    private val listener: OnRecommendItemClickListener
) : PagingDataAdapter<PerfumeRecommendItem, PerfumeRecommendPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

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
        holder.bind(glideRequests, item, listener)
    }

    private fun ViewHolder.bind(
        glideRequests: GlideRequests,
        item: MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem,
        listener: OnRecommendItemClickListener?
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

    interface OnRecommendItemClickListener {
        fun onPerfumeRecommendClick(id: String)
    }
}