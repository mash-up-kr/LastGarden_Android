package com.mashup.lastgarden.ui.scent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.databinding.ItemScentBinding
import com.mashup.lastgarden.extensions.dateConverter
import com.mashup.lastgarden.extensions.numberFormatter

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
        glideRequests.load(item.perfumeImageUrl).into(binding.scentImageView)
        binding.run {
            val pageCount =
                (bindingAdapterPosition + 1).toString() + " / " + list?.size?.let {
                    numberFormatter(it.toLong())
                }
            pageCountTextView.text = pageCount
            profileImageView.setImageUrl(glideRequests, item.perfumeImageUrl)
            nicknameTextView.text = item.userNickname
            dateTextView.text = dateConverter(item.createdAt)
            var tags = ""
            item.tags?.forEach { tags += it.contents + " " }
            tagListTextView.text = tags
            likeCountTextView.text = item.likeCount?.let { numberFormatter(it) }
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