package com.mashup.lastgarden.data.vo

import androidx.room.Entity

@Entity(tableName = "users")
data class User(
    val id: Int,
    val name: String,
    val profileUrl: String?
)