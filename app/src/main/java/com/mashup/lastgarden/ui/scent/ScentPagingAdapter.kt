package com.mashup.lastgarden.ui.scent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.databinding.ItemScentBinding
import com.mashup.lastgarden.utils.convertDate
import com.mashup.lastgarden.utils.formatNumber
import com.mashup.lastgarden.utils.formatPageCount

class ScentPagingAdapter(
    private val glideRequests: GlideRequests,
    private val listener: ScentViewPagerAdapter.OnClickListener? = null
) : PagingDataAdapter<Story, ScentPagingAdapter.ScentViewHolder>(DIFF_CALLBACK) {
    var listSize: Int = 0

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(
                oldItem: Story,
                newItem: Story
            ): Boolean = oldItem.storyId == newItem.storyId

            override fun areContentsTheSame(
                oldItem: Story,
                newItem: Story
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ScentViewHolder {
        return ScentViewHolder(
            ItemScentBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    fun setStoryListSize(size: Int) {
        listSize = size
    }

    class ScentViewHolder(val binding: ItemScentBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onBindViewHolder(viewHolder: ScentViewHolder, position: Int) {
        getItem(position)?.let { viewHolder.bind(it) }
    }

    private fun ScentViewHolder.bind(item: Story) {
        glideRequests.load(item.perfumeImageUrl).into(binding.scentImageView)
        binding.run {
            pageCountTextView.text =
                formatPageCount(pageCountTextView.context, bindingAdapterPosition, itemCount)
            profileImageView.setImageUrl(glideRequests, item.userProfileImage)
            nicknameTextView.text = item.userNickname
            dateTextView.text = convertDate(dateTextView.resources, item.createdAt)
            tagListTextView.text = item.tags?.joinToString(" ") { "#" + it.contents + " " }
            likeCountTextView.text = item.likeCount?.let { formatNumber(it) }
            commentImageView.setOnClickListener { listener?.onCommentClick(item.storyId) }
            likeImageView.setOnClickListener {
                listener?.onLikeClick(item.storyId, bindingAdapterPosition)
            }
            likeImageView.loadImage(
                glideRequests,
                if (item.isLiked) R.drawable.ic_like else R.drawable.ic_dislike
            )
        }
    }
}