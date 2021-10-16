package com.mashup.lastgarden.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfumes")
data class Perfume(
    @PrimaryKey val id: String,
    val name: String,
    val brandName: String,
    val stories: List<Story>? = null,
    val perfumeImage: String? = null,
    val likeCount: Long? = 0L
) {
    data class Story(val id: String, val authorId: String, val title: String, val likeCount: Long? = 0L)
}