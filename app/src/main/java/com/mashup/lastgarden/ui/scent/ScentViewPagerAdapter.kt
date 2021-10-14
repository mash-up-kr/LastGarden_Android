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
        binding.pageCountTextView.text = pageCount
        binding.profileImageView.setImageUrl(glideRequests, item.imgProfile)
        binding.nicknameTextView.text = item.nickname
        binding.tagListTextView.text = TextUtils.join(" ", item.tagList)
        binding.commentCountTextView.text = item.commentCount.toString()
        binding.likeCountTextView.text = item.likeCount.toString()
        when (item.likeState) {
            true -> binding.likeImageView.loadImage(glideRequests, R.drawable.ic_like)
            else -> binding.likeImageView.loadImage(glideRequests, R.drawable.ic_dislike)
        }
        binding.commentImageView.setOnClickListener { listener?.onCommentClick(item.scentId) }
        binding.likeImageView.setOnClickListener { listener?.onLikeClick(item.scentId) }
    }

    class ScentViewHolder(val binding: ItemScentBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface OnClickListener {
        fun onCommentClick(scentId: Int)
        fun onLikeClick(scentId: Int)
    }
}



