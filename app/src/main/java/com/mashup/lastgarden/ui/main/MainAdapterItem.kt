package com.mashup.lastgarden.ui.main

sealed class MainAdapterItem(val id: String) {
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = id.hashCode()

    data class TodayPerfume(
        val perfumeId: String,
        val name: String,
        val perfumeImage: String? = null
    ) : MainAdapterItem(perfumeId)

    data class TodayPerfumeStories(
        val storyItems: List<TodayPerfumeStoryItem>
    ) : MainAdapterItem("TodayPerfumeStories") {

        data class TodayPerfumeStoryItem(
            val id: String,
            val authorName: String,
            val authorProfileImage: String? = null,
            val storyImageUrl: String? = null
        )
    }

    object RefreshAnotherPerfume : MainAdapterItem("RefreshAnotherPerfume")

    object Banner : MainAdapterItem("Banner")

    object HotStoryHeader : MainAdapterItem("HotStoryHeader")

    data class HotStories(
        val stories: List<HotStoryItem>
    ) : MainAdapterItem("HotStory") {
        data class HotStoryItem(
            val id: String,
            val perfumeContentImageUrl: String?,
            val storyImageUrl: String?,
            val authorName: String,
            val authorProfileImage: String?,
            val title: String,
            val likeCount: Long
        )
    }

    object PerfumeRankingsHeader : MainAdapterItem("RankingPerfumesHeader")

    data class PerfumeRankings(
        val perfumeItems: List<PerfumeRankingItem>
    ) : MainAdapterItem("RankingPerfumes") {
        data class PerfumeRankingItem(
            val id: String,
            val rank: Int,
            val imageUrl: String?,
            val brandName: String,
            val name: String
        )
    }

    object PerfumeRecommendsHeader : MainAdapterItem("PerfumeRecommendsHeader")

    data class PerfumeRecommends(
        val perfumeItems: List<PerfumeRecommendItem>
    ) : MainAdapterItem("PerfumeSuggestions") {
        data class PerfumeRecommendItem(
            val id: String,
            val imageUrl: String? = null,
            val brandName: String,
            val name: String,
            val likeCount: Long
        )
    }

    object SeeMore : MainAdapterItem("SeeMore")
}