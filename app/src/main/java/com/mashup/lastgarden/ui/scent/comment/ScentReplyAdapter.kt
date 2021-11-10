package com.mashup.lastgarden.ui.scent.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lastgarden.data.vo.Reply
import com.mashup.lastgarden.databinding.ItemReplyBinding
import com.mashup.lastgarden.extensions.btnThumbsUpDownSelector

class ScentReplyAdapter(
    private val replyItems: List<Reply>
) : RecyclerView.Adapter<ScentReplyAdapter.ScentReplyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScentReplyViewHolder {
        return ScentReplyViewHolder(
            ItemReplyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ScentReplyViewHolder,
        position: Int
    ) {
        holder.bind(replyItems[position])
    }

    override fun getItemCount(): Int {
        return replyItems.size
    }

    private fun ScentReplyViewHolder.bind(item: Reply) {
        binding.run {
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
        RecyclerView.ViewHolder(binding.root)
}