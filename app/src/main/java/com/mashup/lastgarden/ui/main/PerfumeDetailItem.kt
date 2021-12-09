package com.mashup.lastgarden.ui.main

import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.data.vo.Tag

data class PerfumeDetailItem(
    val id: Int,
    val imageUrl: String? = null,
    val userProfileImageUrl: String? = null,
    val userNickname: String,
    val likeCount: Long? = 0L,
    val tags: List<Tag>? = emptyList()
)

fun Story.toPerfumeDetailStoryItem(): PerfumeDetailItem =
    PerfumeDetailItem(
        id = storyId,
        imageUrl = thumbnailUrl,
        userProfileImageUrl = userProfileImage,
        userNickname = userNickname,
        likeCount = likeCount,
        tags = tags
    )