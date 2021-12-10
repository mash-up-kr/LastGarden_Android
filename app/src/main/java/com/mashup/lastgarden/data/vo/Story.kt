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
    @ColumnInfo(name = "story_perfume_image_url") @SerializedName("imageUrl") val perfumeImageUrl: String? = null,
    @Ignore val perfumeThumbnailUrl: String? = null,
    @Ignore val perfumeName: String? = null,
    @ColumnInfo(name = "story_user_id") val userId: Int,
    @ColumnInfo(name = "story_thumbnail_url") val thumbnailUrl: String? = null,
    @ColumnInfo(name = "story_created_date") val createdAt: String,
    @ColumnInfo(name = "story_user_profile_image") @SerializedName("userProfileImageUrl") val userProfileImage: String? = null,
    @ColumnInfo(name = "story_user_nickname") val userNickname: String,
    @ColumnInfo(name = "story_like_count") val likeCount: Long? = 0L,
    @Ignore val tags: List<Tag>? = emptyList(),
    @ColumnInfo(name = "story_liked") val isLiked: Boolean
) {
    constructor(
        storyId: Int,
        userId: Int,
        perfumeImageUrl: String?,
        thumbnailUrl: String?,
        userProfileImage: String?,
        createdAt: String,
        userNickname: String,
        likeCount: Long?,
        isLiked: Boolean
    ) : this(
        storyId = storyId,
        userId = userId,
        perfumeImageUrl = perfumeImageUrl,
        thumbnailUrl = thumbnailUrl,
        createdAt = createdAt,
        userProfileImage = userProfileImage,
        userNickname = userNickname,
        likeCount = likeCount,
        tags = emptyList(),
        isLiked = isLiked
    )
}