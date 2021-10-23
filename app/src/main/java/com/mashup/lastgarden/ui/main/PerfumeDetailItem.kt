package com.mashup.lastgarden.ui.main

import com.mashup.lastgarden.data.vo.Tag

data class PerfumeDetailItem(
    val storyId: Int,
    val thumbnailUrl: String? = null,
    val userProfileImage: String? = null,
    val userNickname: String,
    val likeCount: Long? = 0L,
    val tags: List<Tag>? = emptyList()
)