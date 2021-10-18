package com.mashup.lastgarden.ui.scent

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mashup.base.extensions.loadImage
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.ScentItem
import com.mashup.lastgarden.databinding.ItemScentBinding

class ScentViewPagerAdapter(
    private val list: List<ScentItem>,
    private val glideRequests: GlideRequests,
    private val listener: OnClickListener? = null
) : RecyclerView.Adapter<ScentViewPagerAdapter.ScentViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ScentViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_scent, viewGroup, false)
        return ScentViewHolder(ItemScentBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: ScentViewHolder, position: Int) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun ScentViewHolder.bind(item: ScentItem) {
        Glide.with(binding.root).load(item.imgUrl).into(binding.scentImageView)
        val pageCount = (bindingAdapterPosition + 1).toString() + "/" + list.size
        binding.apply {
            pageCountTextView.text = pageCount
            profileImageView.setImageUrl(glideRequests, item.imgProfile)
            nicknameTextView.text = item.nickname
            dateTextView.text = item.date
            tagListTextView.text = TextUtils.join(" ", item.tagList)
            commentCountTextView.text = item.commentCount.toString()
            likeCountTextView.text = item.likeCount.toString()
            commentImageView.setOnClickListener { listener?.onCommentClick(item.scentId) }
            likeImageView.setOnClickListener { listener?.onLikeClick(item.scentId) }
            when (item.likeState) {
                true -> likeImageView.loadImage(glideRequests, R.drawable.ic_like)
                else -> likeImageView.loadImage(glideRequests, R.drawable.ic_dislike)
            }
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