package com.mashup.lastgarden.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.mashup.lastgarden.data.vo.User
import com.mashup.lastgarden.network.GsonFactory

class PerfumeDatabaseConverters {

    /**
     * below codes are sample code, please remove this codes when defining PerfumeDatabaseConverters
     */

    @TypeConverter
    fun fromUser(user: User?): String? = user?.let { GsonFactory.gson.toJson(it) }

    @TypeConverter
    fun toUser(value: String?): User? =
        GsonFactory.gson.fromJson(value, object : TypeToken<User>() {}.type)
}