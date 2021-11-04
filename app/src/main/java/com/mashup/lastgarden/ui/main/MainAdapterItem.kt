package com.mashup.lastgarden.ui.main

import com.mashup.lastgarden.Constant.PREFIX_PERFUME_ID
import com.mashup.lastgarden.Constant.PREFIX_STORY_ID
import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.Story

sealed class MainAdapterItem(val id: String) {
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = id.hashCode()

    data class TodayPerfume(
        val perfumeId: String,
        val name: String? = "",
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
    ) : MainAdapterItem("HotStories") {
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
            val brandName: String?,
            val name: String?
        )
    }

    object PerfumeRecommendsHeader : MainAdapterItem("PerfumeRecommendsHeader")

    data class PerfumeRecommends(
        val perfumeItems: List<PerfumeRecommendItem>
    ) : MainAdapterItem("PerfumeSuggestions") {
        data class PerfumeRecommendItem(
            val id: String,
            val imageUrl: String? = null,
            val brandName: String?,
            val name: String?,
            val likeCount: Long
        )
    }

    object SeeMore : MainAdapterItem("SeeMore")
}

fun List<Story>.toHotStoryItems(): List<MainAdapterItem.HotStories.HotStoryItem> =
    map { it.toHotStoryItem() }

fun Story.toHotStoryItem(): MainAdapterItem.HotStories.HotStoryItem =
    MainAdapterItem.HotStories.HotStoryItem(
        id = PREFIX_STORY_ID + storyId,
        perfumeContentImageUrl = perfumeImageUrl,
        storyImageUrl = thumbnailUrl,
        authorName = userNickname,
        authorProfileImage = userProfileImage,
        title = "Dummy",
        likeCount = likeCount ?: 0L
    )

fun List<Perfume>.toPerfumeRankingItems(): List<MainAdapterItem.PerfumeRankings.PerfumeRankingItem> =
    mapNotNull { perfume ->
        if (perfume.rank != null) {
            perfume.toPerfumeRankingItem()
        } else {
            null
        }
    }

fun List<Perfume>.toRecommendPerfumeItems(): List<MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem> =
    mapNotNull { perfume -> perfume.toRecommendPerfumeItem() }

fun Perfume.toPerfumeRankingItem(): MainAdapterItem.PerfumeRankings.PerfumeRankingItem =
    MainAdapterItem.PerfumeRankings.PerfumeRankingItem(
        id = PREFIX_PERFUME_ID + perfumeId,
        rank = rank ?: 0,
        imageUrl = thumbnailUrl,
        brandName = brandName,
        name = name ?: perfumeName ?: koreanName
    )

fun Perfume.toMainAdapterItem(): MainAdapterItem = MainAdapterItem.TodayPerfume(
    perfumeId = PREFIX_PERFUME_ID + perfumeId,
    name = name ?: perfumeName ?: koreanName,
    perfumeImage = thumbnailUrl
)

fun Perfume.toRecommendPerfumeItem(): MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem =
    MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem(
        id = PREFIX_PERFUME_ID + perfumeId,
        imageUrl = thumbnailUrl,
        brandName = brandName,
        name = name ?: perfumeName ?: koreanName,
        likeCount = likeCount ?: 0L
    )

fun List<Story>.toTodayPerfumeStoryItems(): List<MainAdapterItem.TodayPerfumeStories.TodayPerfumeStoryItem> =
    map { story ->
        MainAdapterItem.TodayPerfumeStories.TodayPerfumeStoryItem(
            id = PREFIX_STORY_ID + story.storyId,
            authorName = story.userNickname,
            authorProfileImage = story.userProfileImage,
            storyImageUrl = story.thumbnailUrl
        )
    }

fun List<MainAdapterItem.TodayPerfumeStories.TodayPerfumeStoryItem>.toMainAdapterItem(): MainAdapterItem =
    MainAdapterItem.TodayPerfumeStories(this)