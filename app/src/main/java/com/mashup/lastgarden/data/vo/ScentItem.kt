package com.mashup.lastgarden.data.vo

data class ScentItem(
    val imgUrl: String = "",
    val imgProfile: String = "",
    val nickName: String = "",
    val tagList: List<String>,
    val commentCount: Int,
    val likeCount: Int
)

