package com.mashup.lastgarden.data.vo

data class PerfumeDetailData(
    val photo: String,
    val profileImage: String? = null,
    val name: String,
    val tags: String,
    val likeCount: Long
)