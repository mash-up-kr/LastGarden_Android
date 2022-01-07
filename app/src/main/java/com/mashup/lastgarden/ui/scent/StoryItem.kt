package com.mashup.lastgarden.ui.scent

import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.data.vo.Tag

data class StoryItem(
    val id: Int,
    val isLiked: Boolean,
    val imageUrl: String? = null,
    val userProfileImage: String? = null,
    val nickname: String,
    val createdAt: String,
    val likeCount: Long? = null,
    val tags: List<Tag> = emptyList()
)

fun Story.toStoryItem() = StoryItem(
    id = storyId,
    isLiked = isLiked,
    imageUrl = imageUrl,
    userProfileImage = userProfileImage,
    nickname = userNickname,
    createdAt = createdAt,
    likeCount = likeCount,
    tags = tags ?: emptyList()
)