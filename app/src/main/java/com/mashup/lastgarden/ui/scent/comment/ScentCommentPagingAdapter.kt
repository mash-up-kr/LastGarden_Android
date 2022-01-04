package com.mashup.lastgarden.ui.scent.comment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.databinding.ItemCommentBinding
import com.mashup.lastgarden.utils.StringFormatter

class ScentCommentPagingAdapter(
    private val glideRequests: GlideRequests,
    private val listener: OnClickListener? = null
) : PagingDataAdapter<Comment, ScentCommentPagingAdapter.ScentCommentViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(
                oldItem: Comment,
                newItem: Comment
            ): Boolean = oldItem.commentId == newItem.commentId

            override fun areContentsTheSame(
                oldItem: Comment,
                newItem: Comment
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ScentCommentViewHolder {
        return ScentCommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    private fun ScentCommentViewHolder.bind(item: Comment) {
        binding.run {
            nicknameTextView.text = item.userNickname
            dateTextView.text = StringFormatter.convertDate(dateTextView.resources, item.createdAt)
            contentTextView.text = item.contents
            replyCountTextView.text =
                replyCountTextView.resources.getString(R.string.reply_count, item.replyCount)
            commentLikeButton.text = item.likeCount.toString()
            commentDislikeButton.text = item.dislikeCount.toString()
        }
        binding.replyCountTextView.setOnClickListener { listener?.onReplyClick(item) }
    }

    class ScentCommentViewHolder(
        val binding: ItemCommentBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(viewHolder: ScentCommentViewHolder, position: Int) {
        getItem(position)?.let { viewHolder.bind(it) }
    }

    interface OnClickListener {
        fun onReplyClick(comment: Comment)
    }
}