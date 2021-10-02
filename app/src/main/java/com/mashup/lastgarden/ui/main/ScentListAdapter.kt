package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.data.vo.ScentListData
import com.mashup.lastgarden.databinding.ItemPerfumeDetailBinding

class ScentListAdapter(private val glideRequests: GlideRequests) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = mutableListOf<ScentListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemPerfumeDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ScentListViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ScentListViewHolder(private val binding: ItemPerfumeDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val item = binding.perfumeCardView

        fun bind(data: ScentListData) {
            binding.perfumeCardView.setUserImage(glideRequests, data.profileImage)
            binding.perfumeCardView.setSourceImage(glideRequests, data.photo)
            binding.perfumeCardView.userName = data.name
            binding.perfumeCardView.title = data.tags
            binding.perfumeCardView.count = data.likeCount
        }
    }
}
