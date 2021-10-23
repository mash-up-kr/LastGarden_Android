package com.mashup.lastgarden.ui.main

import androidx.lifecycle.ViewModel
import com.mashup.lastgarden.data.vo.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PerfumeDetailViewModel @Inject constructor() : ViewModel() {

    private val _perfumeDetailItems = MutableStateFlow<PerfumeDetailData?>(null)
    val perfumeDetailItems: StateFlow<PerfumeDetailData?> = _perfumeDetailItems

    init {
        _perfumeDetailItems.value = PerfumeDetailData(
            listOf(
                PerfumeDetailData.PerfumeDetailItem(
                    storyId = 1,
                    thumbnailUrl = "https://images.unsplash.com/photo-1633178082360-4f2b133c7399?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                    userProfileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    userNickname = "seehyang",
                    likeCount = 200,
                    tags = listOf(
                        Tag(tagId = 10, contents = "플로럴"),
                        Tag(tagId = 11, contents = "우디"),
                        Tag(tagId = 12, contents = "머스크")
                    )
                ), PerfumeDetailData.PerfumeDetailItem(
                    storyId = 2,
                    thumbnailUrl = "https://images.unsplash.com/photo-1633090332452-532d6b39422a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2516&q=80",
                    userProfileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    userNickname = "seehyang",
                    likeCount = 100,
                    tags = listOf(
                        Tag(tagId = 12, contents = "머스크")
                    )
                ), PerfumeDetailData.PerfumeDetailItem(
                    storyId = 3,
                    thumbnailUrl = "https://images.unsplash.com/photo-1633074320366-365b5e382fb5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1374&q=80",
                    userProfileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    userNickname = "seehyang",
                    likeCount = 300,
                    tags = listOf(
                        Tag(tagId = 12, contents = "머스크"),
                        Tag(tagId = 13, contents = "시트러스")
                    )
                ), PerfumeDetailData.PerfumeDetailItem(
                    storyId = 4,
                    thumbnailUrl = "https://images.unsplash.com/photo-1633124890681-fc9afca4c4ac?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                    userProfileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    userNickname = "seehyang",
                    likeCount = 250,
                    tags = listOf(
                        Tag(tagId = 10, contents = "플로럴"),
                        Tag(tagId = 11, contents = "우디"),
                        Tag(tagId = 12, contents = "머스크")
                    )
                ), PerfumeDetailData.PerfumeDetailItem(
                    storyId = 5,
                    thumbnailUrl = "https://images.unsplash.com/photo-1633131902932-31ba72ee19d7?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1470&q=80",
                    userProfileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    userNickname = "seehyang",
                    likeCount = 90,
                    tags = listOf(
                        Tag(tagId = 12, contents = "머스크")
                    )
                ), PerfumeDetailData.PerfumeDetailItem(
                    storyId = 6,
                    thumbnailUrl = "https://images.unsplash.com/photo-1633166158652-22faaa722751?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                    userProfileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    userNickname = "seehyang",
                    likeCount = 50,
                    tags = listOf(
                        Tag(tagId = 12, contents = "머스크"),
                        Tag(tagId = 13, contents = "시트러스")
                    )
                )
            )
        )
    }
}