package com.mashup.lastgarden.ui.scent.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Reply
import com.mashup.lastgarden.databinding.ItemReplyBinding
import com.mashup.lastgarden.extensions.btnThumbsUpDownSelector

class ScentReplyAdapter(
    private val list: List<Reply>
) : RecyclerView.Adapter<ScentReplyAdapter.ScentReplyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScentReplyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reply, parent, false)
        return ScentReplyViewHolder(ItemReplyBinding.bind(view))
    }

    override fun onBindViewHolder(
        holder: ScentReplyViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun ScentReplyViewHolder.bind(item: Reply) {
        binding.apply {
            includeDetailLayout.nicknameTextView.text = item.nickName
            includeDetailLayout.dateTextView.text = item.date
            includeDetailLayout.contentTextView.text = item.content
            includeDetailLayout.replyCountTextView.visibility = View.INVISIBLE
            includeDetailLayout.commentLikeButton.text = item.likeCount.toString()
            includeDetailLayout.commentDislikeButton.text = item.dislikeCount.toString()
            divider.visibility = View.GONE
        }
        btnThumbsUpDownSelector(
            binding.includeDetailLayout.commentLikeButton,
            binding.includeDetailLayout.commentDislikeButton,
            item.likeState,
            itemView.context
        )
    }

    class ScentReplyViewHolder(val binding: ItemReplyBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}