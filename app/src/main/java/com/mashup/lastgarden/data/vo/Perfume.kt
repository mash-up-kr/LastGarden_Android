package com.mashup.lastgarden.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "perfumes")
data class Perfume(
    @PrimaryKey @ColumnInfo(name = "perfume_id") @SerializedName("id") val perfumeId: Int,
    @ColumnInfo(name = "perfume_name") val name: String? = null,
    @ColumnInfo(name = "perfume_thumbnail_url") val thumbnailUrl: String? = null,
    @ColumnInfo(name = "perfume_korean_name") val koreanName: String? = null,
    @ColumnInfo(name = "perfume_brand_id") val brandId: String,
    @ColumnInfo(name = "perfume_brand_name") val brandName: String? = null,
    @ColumnInfo(name = "perfume_like_count") val likeCount: Long? = 0L,
    @ColumnInfo(name = "perfume_second_name") @SerializedName("perfume_name") val perfumeName: String? = null,
    @ColumnInfo(name = "perfume_is_liked") val isLiked: Boolean = false,
    @Ignore val notes: NoteContainer? = null,
    @Ignore val rank: Int? = null,
    @Ignore val accords: List<Note>? = null
) {
    constructor(
        perfumeId: Int,
        name: String,
        thumbnailUrl: String? = null,
        koreanName: String,
        brandId: String,
        brandName: String,
        perfumeName: String?,
        isLiked: Boolean = false,
        likeCount: Long? = 0L
    ) : this(
        perfumeId = perfumeId,
        name = name,
        thumbnailUrl = thumbnailUrl,
        koreanName = koreanName,
        brandId = brandId,
        brandName = brandName,
        likeCount = likeCount,
        perfumeName = perfumeName,
        isLiked = isLiked,
        notes = null,
        rank = null,
        accords = null
    )

    data class NoteContainer(
        val top: List<Note>? = emptyList(),
        val middle: List<Note>? = emptyList(),
        val base: List<Note>? = emptyList(),
        val default: List<Note>? = emptyList()
    )
}