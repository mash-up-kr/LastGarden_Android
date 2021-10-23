package com.mashup.lastgarden.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tags")
data class Tag(
    @PrimaryKey @ColumnInfo(name = "tag_id") @SerializedName("id") val tagId: Int,
    @ColumnInfo(name = "tag_contents") val contents: String
)