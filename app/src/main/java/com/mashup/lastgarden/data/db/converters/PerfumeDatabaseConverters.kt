package com.mashup.lastgarden.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.mashup.lastgarden.data.vo.Tag
import com.mashup.lastgarden.network.GsonFactory

class PerfumeDatabaseConverters {
    @TypeConverter
    fun fromTags(tags: List<Tag>?): String? = tags?.let(GsonFactory.gson::toJson)

    @TypeConverter
    fun toTags(value: String?): List<Tag>? =
        GsonFactory.gson.fromJson(value, object : TypeToken<List<Tag>>() {}.type)
}