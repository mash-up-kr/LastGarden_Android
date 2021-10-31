package com.mashup.lastgarden.data.vo

data class PerfumeAndStories(
    val perfume: Perfume? = null,
    val stories: List<Story>? = emptyList()
)