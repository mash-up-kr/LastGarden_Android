package com.mashup.lastgarden.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey @ColumnInfo(name = "comment_id") @SerializedName("id") val commentId: Int,
    @ColumnInfo(name = "comment_user_nickname") val userNickname: String,
    @ColumnInfo(name = "comment_created_date") val createdAt: String,
    @ColumnInfo(name = "comment_contents") val contents: String? = null,
    @ColumnInfo(name = "comment_reply_count") val replyCount: Int? = 0,
    @ColumnInfo(name = "comment_like_count") val likeCount: Int? = 0,
    @ColumnInfo(name = "comment_dislike_count") val dislikeCount: Int? = 0
)