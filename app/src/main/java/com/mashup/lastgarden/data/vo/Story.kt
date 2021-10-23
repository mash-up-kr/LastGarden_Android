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
    @ColumnInfo(name = "story_perfume_image_url") val perfumeImageUrl: String? = null,
    @ColumnInfo(name = "story_thumbnail_url") val thumbnailUrl: String? = null,
    @ColumnInfo(name = "story_user_profile_image") val userProfileImage: String? = null,
    @ColumnInfo(name = "story_user_nickname") val userNickname: String,
    @ColumnInfo(name = "story_like_count") val likeCount: Long? = 0L,
    @Ignore val tags: List<Tag>? = emptyList()
) {
    constructor(
        storyId: Int,
        perfumeImageUrl: String?,
        thumbnailUrl: String?,
        userProfileImage: String?,
        userNickname: String,
        likeCount: Long?
    ) : this(
        storyId = storyId,
        perfumeImageUrl = perfumeImageUrl,
        thumbnailUrl = thumbnailUrl,
        userProfileImage = userProfileImage,
        userNickname = userNickname,
        likeCount = likeCount,
        tags = emptyList()
    )
}