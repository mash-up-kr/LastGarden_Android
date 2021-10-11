package com.mashup.lastgarden.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.data.vo.PerfumeDetailData
import com.mashup.lastgarden.databinding.ItemPerfumeDetailBinding

class PerfumeDetailAdapter(private val glideRequests: GlideRequests) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = mutableListOf<PerfumeDetailData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemPerfumeDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ScentListViewHolder).bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    private inner class ScentListViewHolder(private val binding: ItemPerfumeDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PerfumeDetailData) {
            binding.perfumeCardView.setUserImage(glideRequests, data.profileImage)
            binding.perfumeCardView.setSourceImage(glideRequests, data.photo)
            binding.perfumeCardView.userName = data.name
            binding.perfumeCardView.title = data.tags
            binding.perfumeCardView.count = data.likeCount
        }
    }
}