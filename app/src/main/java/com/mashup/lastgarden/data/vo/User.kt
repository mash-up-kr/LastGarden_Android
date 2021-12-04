package com.mashup.lastgarden.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mashup.lastgarden.ui.sign.AuthType
import com.mashup.lastgarden.ui.sign.GenderType

sealed class UserState

object NoneUser : UserState()

@Entity(tableName = "users")
data class User(
    @PrimaryKey @ColumnInfo(name = "user_id") @SerializedName("id") val id: Int?,
    @ColumnInfo(name = "nickname") val nickname: String?,
    @ColumnInfo(name = "user_profile_url") val profileImage: String? = null,
    val email: String,
    val age: Int?,
    val gender: GenderType?,
    val isActivated: Boolean,
    val oauthType: AuthType
) : UserState() {
    fun isEmpty() = nickname.isNullOrEmpty() || age == null || gender == null
}