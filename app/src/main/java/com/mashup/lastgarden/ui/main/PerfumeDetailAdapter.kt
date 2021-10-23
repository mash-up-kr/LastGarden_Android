package com.mashup.lastgarden.ui.main

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
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
                ): Boolean = oldItem.storyId == newItem.storyId

                override fun areContentsTheSame(
                    oldItem: PerfumeDetailItem,
                    newItem: PerfumeDetailItem
                ): Boolean = oldItem == newItem
            }
    }

    init {
        setHasStableIds(true)
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
        binding.perfumeCardView.setUserImage(glideRequests, data.userProfileImage)
        binding.perfumeCardView.setSourceImage(glideRequests, data.thumbnailUrl)
        binding.perfumeCardView.userName = data.userNickname
        binding.perfumeCardView.count = data.likeCount
        if (data.tags != null)
            binding.perfumeCardView.title =
                TextUtils.join(" ", data.tags.map { tag -> "#${tag.contents}" })
    }
}