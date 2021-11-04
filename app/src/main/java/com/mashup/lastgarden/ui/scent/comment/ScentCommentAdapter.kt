package com.mashup.lastgarden.ui.scent.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.databinding.ItemCommentBinding
import com.mashup.lastgarden.extensions.btnThumbsUpDownSelector

class ScentCommentAdapter(
    private val list: List<Comment>,
    private val listener: OnClickListener? = null
) : RecyclerView.Adapter<ScentCommentAdapter.ScentCommentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScentCommentViewHolder {
        return ScentCommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ScentCommentViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun ScentCommentViewHolder.bind(item: Comment) {
        binding.run {
            nicknameTextView.text = item.nickName
            dateTextView.text = item.date
            contentTextView.text = item.content
            replyCountTextView.text = "답글 " + item.replyCount.toString()
            commentLikeButton.text = item.likeCount.toString()
            commentDislikeButton.text = item.dislikeCount.toString()
        }
        btnThumbsUpDownSelector(
            binding.commentLikeButton,
            binding.commentDislikeButton,
            item.likeState,
            itemView.context
        )
        binding.replyCountTextView.setOnClickListener { listener?.onReplyClick(item) }
    }

    class ScentCommentViewHolder(
        val binding: ItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    }

    interface OnClickListener {
        fun onReplyClick(comment: Comment)
    }
}