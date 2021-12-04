package com.mashup.lastgarden.ui.upload.perfume

sealed class PerfumeItem {
    object EmptyPerfume : PerfumeItem()

    data class PerfumeSearchedItem(
        val id: Int,
        val imageUrl: String? = null,
        val brandName: String?,
        val name: String?,
        val likeCount: Long,
        val isSelected: Boolean
    ) : PerfumeItem()
}