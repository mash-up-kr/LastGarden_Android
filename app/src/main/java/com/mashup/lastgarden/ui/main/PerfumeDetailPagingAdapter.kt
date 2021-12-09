package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.ItemPerfumeDetailBinding

class PerfumeDetailPagingAdapter(
    private val glideRequests: GlideRequests
) : PagingDataAdapter<PerfumeDetailItem, PerfumeDetailViewHolder>(DIFF_CALLBACK) {
    private lateinit var storyClickListener: OnItemClickListener

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PerfumeDetailItem>() {
            override fun areItemsTheSame(
                oldItem: PerfumeDetailItem,
                newItem: PerfumeDetailItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PerfumeDetailItem,
                newItem: PerfumeDetailItem
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfumeDetailViewHolder {
        return PerfumeDetailViewHolder(
            ItemPerfumeDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PerfumeDetailViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(glideRequests, item)
        holder.itemView.setOnClickListener {
            storyClickListener.onStoryItemClick(item.id, position)
        }
    }

    interface OnItemClickListener {
        fun onStoryItemClick(storyId: Int, storyIndex: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.storyClickListener = onItemClickListener
    }
}