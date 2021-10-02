package com.mashup.lastgarden.ui.scent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.ScentItem
import com.mashup.lastgarden.databinding.ItemScentViewpagerBinding

class ScentViewPagerAdapter(private val list: List<ScentItem>) :
    RecyclerView.Adapter<ScentViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ScentViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_scent_viewpager, viewGroup, false)
        return ScentViewHolder(ItemScentViewpagerBinding.bind(view))
    }

    override fun onBindViewHolder(viewHolder: ScentViewHolder, position: Int) {
        viewHolder.binding.tvCount.text = (position + 1).toString() + " / " + list.size
        var str = ""
        val iterator = list[position].tagList.iterator()
        while (iterator.hasNext()) {
            str += "#" + iterator.next() + " "
        }
        viewHolder.binding.tvTag.text = str
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ScentViewHolder(val binding: ItemScentViewpagerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ScentItem) {
        Glide.with(binding.imgScent.context).load(item.imgUrl)
            .into(binding.imgScent)
        Glide.with(binding.imgProfile.context).load(item.imgProfile)
            .into(binding.imgProfile)
        binding.tvNickname.text = item.nickName
        binding.tvCommentCount.text = item.commentCount.toString()
        binding.tvLikeCount.text = item.likeCount.toString()
    }
}