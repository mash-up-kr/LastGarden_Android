package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.customview.TopBottomGradientCardView
import com.mashup.lastgarden.databinding.ItemTodayPerfumeStoryBinding

private typealias TodayPerfumeStoryItem = MainAdapterItem.TodayPerfumeStories.TodayPerfumeStoryItem

class TodayPerfumeStoryAdapter(
    private val glideRequests: GlideRequests
) : ListAdapter<TodayPerfumeStoryItem, TodayPerfumeStoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TodayPerfumeStoryItem>() {
            override fun areItemsTheSame(
                oldItem: TodayPerfumeStoryItem,
                newItem: TodayPerfumeStoryItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TodayPerfumeStoryItem,
                newItem: TodayPerfumeStoryItem
            ): Boolean = oldItem == newItem
        }
    }

    class ViewHolder(binding: ItemTodayPerfumeStoryBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTodayPerfumeStoryBinding.inflate(
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

    private fun ViewHolder.bind(item: TodayPerfumeStoryItem) {
        if (itemView !is TopBottomGradientCardView) return

        itemView.apply {
            userName = item.authorName
            setUserImage(glideRequests, item.authorProfileImage)
            setSourceImage(glideRequests, item.storyImageUrl, R.drawable.ic_story_empty_horizontal)
        }
    }
}