package com.mashup.lastgarden.data.vo

data class Comment(
    val commentId: Int,
    val nickName: String = "",
    val date: String = "",
    val content: String = "",
    val replyList: List<Reply>,
    val replyCount: Int,
    val likeCount: Int,
    val dislikeCount: Int,
    val likeState: Boolean
)