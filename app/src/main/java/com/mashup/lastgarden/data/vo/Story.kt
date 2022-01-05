package com.mashup.lastgarden.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/** TODO
아래 Story에 user가 prefix로 붙는 field 들은 추후 제거될 예정입니다.
 */
@Entity(tableName = "stories")
data class Story(
    @PrimaryKey @ColumnInfo(name = "story_id") @SerializedName("id") val storyId: Int,
    @ColumnInfo(name = "story_perfume_image_url") @SerializedName("imageUrl") val imageUrl: String? = null,
    @ColumnInfo(name = "story_perfume_thumbnail_url") val perfumeThumbnailUrl: String? = null,
    @ColumnInfo(name = "story_perfume_name") val perfumeName: String? = null,
    @ColumnInfo(name = "story_user_id") val userId: Int,
    @ColumnInfo(name = "story_thumbnail_url") val thumbnailUrl: String? = null,
    @ColumnInfo(name = "story_created_date") val createdAt: String,
    @ColumnInfo(name = "story_user_profile_image") @SerializedName("userProfileImageUrl") val userProfileImage: String? = null,
    @ColumnInfo(name = "story_user_nickname") val userNickname: String,
    @ColumnInfo(name = "story_comment_count") val commentCount: Long? = 0L,
    @ColumnInfo(name = "story_like_count") val likeCount: Long? = 0L,
    @Ignore val tags: List<Tag>? = emptyList()
) {
    constructor(
        storyId: Int,
        userId: Int,
        imageUrl: String?,
        perfumeThumbnailUrl: String?,
        perfumeName: String?,
        thumbnailUrl: String?,
        userProfileImage: String?,
        createdAt: String,
        userNickname: String,
        commentCount: Long?,
        likeCount: Long?
    ) : this(
        storyId = storyId,
        userId = userId,
        imageUrl = imageUrl,
        perfumeThumbnailUrl = perfumeThumbnailUrl,
        perfumeName = perfumeName,
        thumbnailUrl = thumbnailUrl,
        createdAt = createdAt,
        userProfileImage = userProfileImage,
        userNickname = userNickname,
        commentCount = commentCount,
        likeCount = likeCount,
        tags = emptyList()
    )
}