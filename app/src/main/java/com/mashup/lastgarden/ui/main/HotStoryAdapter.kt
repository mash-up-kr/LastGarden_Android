package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.customview.PerfumeCardView
import com.mashup.lastgarden.databinding.ItemHotStoryBinding

private typealias HotStoryItem = MainAdapterItem.HotStories.HotStoryItem

class HotStoryAdapter(
    private val glideRequests: GlideRequests
) : ListAdapter<HotStoryItem, HotStoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    lateinit var listener: TodayPerfumeStoryAdapter.OnTodayPerfumeStoryClickListener

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HotStoryItem>() {
            override fun areItemsTheSame(
                oldItem: HotStoryItem,
                newItem: HotStoryItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: HotStoryItem,
                newItem: HotStoryItem
            ): Boolean = oldItem == newItem
        }
    }

    class ViewHolder(binding: ItemHotStoryBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHotStoryBinding.inflate(
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

    private fun ViewHolder.bind(item: HotStoryItem) {
        if (itemView !is PerfumeCardView) return

        itemView.apply {
            title = item.title
            userName = item.authorName
            count = item.likeCount
            isContentImageVisible = true
            setContentImage(glideRequests, item.perfumeContentImageUrl)
            setSourceImage(glideRequests, item.storyImageUrl, R.drawable.ic_story_empty_vertical)
            setUserImage(glideRequests, item.authorProfileImage)
            setOnClickListener {
                listener.hotPerfumeStoryClick(bindingAdapterPosition)
            }
        }
    }

    fun setOnTodayPerfumeStoryClickListener(listener: TodayPerfumeStoryAdapter.OnTodayPerfumeStoryClickListener) {
        this.listener = listener
    }
}