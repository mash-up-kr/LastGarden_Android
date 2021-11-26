package com.mashup.lastgarden.ui.scent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.databinding.ItemScentBinding
import com.mashup.lastgarden.utils.convertDate
import com.mashup.lastgarden.utils.formatNumber

class ScentViewPagerAdapter(
    private val list: List<Story?>?,
    private val glideRequests: GlideRequests,
    private val listener: OnClickListener? = null
) : RecyclerView.Adapter<ScentViewPagerAdapter.ScentViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ScentViewHolder {
        return ScentViewHolder(
            ItemScentBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ScentViewHolder, position: Int) {
        list?.get(position)?.let { viewHolder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    private fun ScentViewHolder.bind(item: Story) {
        bindImageView(item)
        bindTextView(item)
    }

    private fun ScentViewHolder.bindImageView(item: Story) {
        binding.run {
            glideRequests.load(item.perfumeImageUrl).into(binding.scentImageView)
            profileImageView.setImageUrl(glideRequests, item.userProfileImage)
            commentImageView.setOnClickListener { listener?.onCommentClick(item.storyId) }
            likeImageView.setOnClickListener { listener?.onLikeClick(item.storyId) }
            likeImageView.loadImage(glideRequests, R.drawable.ic_dislike)
            //TODO like 이미지 설정
        }
    }

    private fun ScentViewHolder.bindTextView(item: Story) {
        binding.run {
            val pageCount =
                (bindingAdapterPosition + 1).toString() + " / " + list?.size?.let {
                    formatNumber(it.toLong())
                }
            pageCountTextView.text = pageCount
            nicknameTextView.text = item.userNickname
            dateTextView.text = convertDate(binding.dateTextView.context.resources, item.createdAt)
            tagListTextView.text = item.tags?.joinToString(" ") { "#" + it.contents + " " }
            likeCountTextView.text = item.likeCount?.let { formatNumber(it) }
            commentImageView.setOnClickListener { listener?.onCommentClick(item.storyId) }
            likeImageView.setOnClickListener { listener?.onLikeClick(item.storyId) }
            likeImageView.loadImage(glideRequests, R.drawable.ic_dislike)
            //TODO like 이미지 설정
        }
    }

    class ScentViewHolder(val binding: ItemScentBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface OnClickListener {
        fun onCommentClick(scentId: Int)
        fun onLikeClick(scentId: Int)
    }
}