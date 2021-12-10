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
import com.mashup.lastgarden.utils.Util

class ScentPagingAdapter(
    private val glideRequests: GlideRequests,
    private val listener: OnClickListener? = null
) : PagingDataAdapter<Story, ScentPagingAdapter.ScentViewHolder>(DIFF_CALLBACK) {

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

    class ScentViewHolder(
        val binding: ItemScentBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(viewHolder: ScentViewHolder, position: Int) {
        getItem(position)?.let { viewHolder.bind(it) }
    }

    private fun ScentViewHolder.bind(item: Story) {
        glideRequests.load(item.perfumeImageUrl).into(binding.scentImageView)
        binding.run {
            pageCountTextView.text =
                Util.formatPageCount(pageCountTextView.context, bindingAdapterPosition, itemCount)
            profileImageView.setImageUrl(glideRequests, item.userProfileImage)
            nicknameTextView.text = item.userNickname
            dateTextView.text = Util.convertDate(dateTextView.resources, item.createdAt)
            tagListTextView.text = item.tags?.joinToString(" ") { "#" + it.contents + " " }
            likeCountTextView.text = item.likeCount?.let { Util.formatNumber(it) }
            commentImageView.setOnClickListener { listener?.onCommentClick(item.storyId) }
            likeImageView.setOnClickListener { listener?.onLikeClick(item.storyId) }
            likeImageView.loadImage(glideRequests, R.drawable.ic_dislike)
            //TODO like 이미지 설정
        }
    }

    interface OnClickListener {
        fun onCommentClick(scentId: Int)
        fun onLikeClick(scentId: Int)
    }

}