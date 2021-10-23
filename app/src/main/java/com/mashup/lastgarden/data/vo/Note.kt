package com.mashup.lastgarden.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey @ColumnInfo(name = "note_id") @SerializedName("id") val noteId: Int,
    @ColumnInfo(name = "note_name") val name: String,
    @ColumnInfo(name = "note_korean_name") val koreanName: String
)