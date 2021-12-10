package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.databinding.ItemPerfumeRecommendBinding

class PerfumeRecommendAdapter(
    private val glideRequests: GlideRequests,
    private val listener: MainAdapter.OnMainItemClickListener? = null
) : ListAdapter<PerfumeRecommendItem, PerfumeRecommendViewHolder>(DIFF_CALLBACK) {

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

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

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
        if (position !in 0 until itemCount) return
        holder.bind(glideRequests, getItem(position), listener)
    }
}