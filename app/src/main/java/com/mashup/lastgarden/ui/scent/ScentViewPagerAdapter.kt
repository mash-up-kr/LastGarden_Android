package com.mashup.lastgarden.ui.scent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ItemScentBinding
import com.mashup.lastgarden.utils.StringFormatter

class ScentViewPagerAdapter(
    private val glideRequests: GlideRequests,
    private val listener: OnClickListener? = null
) : ListAdapter<StoryItem, ScentViewPagerAdapter.ScentViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val PAYLOAD_LIKE = "payload_like"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(old: StoryItem, new: StoryItem): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: StoryItem, new: StoryItem): Boolean = old == new

            override fun getChangePayload(oldItem: StoryItem, newItem: StoryItem): Any? =
                if (oldItem.copy(isLiked = newItem.isLiked) == newItem) {
                    PAYLOAD_LIKE
                } else {
                    null
                }
        }
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = if (position in 0 until itemCount) {
        getItem(position)?.id?.toLong() ?: RecyclerView.NO_ID
    } else {
        RecyclerView.NO_ID
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ScentViewHolder =
        ScentViewHolder(
            ItemScentBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: ScentViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ScentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) onBindViewHolder(holder, position)

        payloads.forEach { payload ->
            when (payload) {
                PAYLOAD_LIKE -> holder.bindIsLiked(getItem(position).isLiked)
            }
        }
    }

    private fun ScentViewHolder.bind(item: StoryItem) {
        bindImageView(item)
        bindTextView(item)
        bindIsLiked(item.isLiked)
    }

    private fun ScentViewHolder.bindImageView(item: StoryItem) {
        binding.run {
            glideRequests.load(item.imageUrl).into(binding.scentImageView)
            profileImageView.setImageUrl(glideRequests, item.userProfileImage)
            commentImageView.setOnClickListener { listener?.onCommentClick(item.id) }
            likeImageView.setOnClickListener {
                listener?.onLikeClick(item.id, bindingAdapterPosition)
            }
            likeImageView.loadImage(
                glideRequests = glideRequests,
                drawableResId = if (item.isLiked) R.drawable.ic_like else R.drawable.ic_dislike
            )
        }
    }

    private fun ScentViewHolder.bindTextView(item: StoryItem) {
        binding.run {
            pageCountTextView.text = StringFormatter.formatPageCount(
                context = pageCountTextView.context,
                page = bindingAdapterPosition,
                size = itemCount
            )
            nicknameTextView.text = item.nickname
            dateTextView.text = StringFormatter.convertDate(
                resources = binding.dateTextView.context.resources,
                date = item.createdAt
            )
            tagListTextView.isVisible = item.tags.isNotEmpty()
            tagListTextView.text = item.tags.joinToString(" ") { "#" + it.contents + " " }
            likeCountTextView.text = item.likeCount?.let { StringFormatter.formatNumber(it) }
            commentImageView.setOnClickListener { listener?.onCommentClick(item.id) }
            likeImageView.setOnClickListener {
                listener?.onLikeClick(
                    scentId = item.id,
                    storyPosition = bindingAdapterPosition
                )
            }
        }
    }

    private fun ScentViewHolder.bindIsLiked(isLiked: Boolean) {
        binding.likeImageView.loadImage(
            glideRequests = glideRequests,
            drawableResId = if (isLiked) R.drawable.ic_like else R.drawable.ic_dislike
        )
    }

    class ScentViewHolder(val binding: ItemScentBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onCommentClick(scentId: Int)
        fun onLikeClick(scentId: Int, storyPosition: Int)
    }
}