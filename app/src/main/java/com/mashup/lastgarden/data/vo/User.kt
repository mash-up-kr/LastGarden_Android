package com.mashup.lastgarden.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "user_id") @SerializedName("id") val userId: Int,
    @ColumnInfo(name = "user_name") val name: String,
    @ColumnInfo(name = "user_profile_url") val profileUrl: String? = null
)