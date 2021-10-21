package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.data.vo.PerfumeDetailData
import com.mashup.lastgarden.databinding.ItemPerfumeDetailBinding

private typealias PerfumeDetailItem = PerfumeDetailData.PerfumeDetailItem

class PerfumeDetailAdapter(private val glideRequests: GlideRequests) :
    ListAdapter<PerfumeDetailItem, ScentListViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<PerfumeDetailItem>() {
                override fun areItemsTheSame(
                    oldItem: PerfumeDetailItem,
                    newItem: PerfumeDetailItem
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: PerfumeDetailItem,
                    newItem: PerfumeDetailItem
                ): Boolean = oldItem == newItem
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScentListViewHolder {
        val binding =
            ItemPerfumeDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScentListViewHolder(binding, glideRequests)
    }

    override fun onBindViewHolder(holder: ScentListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ScentListViewHolder(
    private val binding: ItemPerfumeDetailBinding,
    private val glideRequests: GlideRequests
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PerfumeDetailItem) {
        binding.perfumeCardView.setUserImage(glideRequests, data.profileImage)
        binding.perfumeCardView.setSourceImage(glideRequests, data.photo)
        binding.perfumeCardView.userName = data.name
        binding.perfumeCardView.title = data.tags
        binding.perfumeCardView.count = data.likeCount
    }
}