package com.mashup.lastgarden.data.vo

data class Reply(
    val replyId: Int,
    val nickName: String = "",
    val date: String = "",
    val content: String = "",
    val likeCount: Int,
    val dislikeCount: Int,
    val likeState: Boolean
)