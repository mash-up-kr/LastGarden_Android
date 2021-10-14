package com.mashup.lastgarden.data.vo

data class ScentItem(
    val scentId: Int,
    val imgUrl: String? = null,
    val imgProfile: String? = null,
    val nickname: String = "",
    val tagList: List<String>,
    val commentCount: Int,
    val likeCount: Int,
    val likeState: Boolean
)