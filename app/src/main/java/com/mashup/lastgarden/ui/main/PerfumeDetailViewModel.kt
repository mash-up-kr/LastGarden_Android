package com.mashup.lastgarden.ui.main

import androidx.lifecycle.ViewModel
import com.mashup.lastgarden.data.vo.PerfumeDetailData
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
                    photo = "https://images.unsplash.com/photo-1633178082360-4f2b133c7399?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                    profileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    name = "seehyang",
                    tags = "#플로럴 #우디 #머스크",
                    likeCount = 200
                ), PerfumeDetailData.PerfumeDetailItem(
                    photo = "https://images.unsplash.com/photo-1633090332452-532d6b39422a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2516&q=80",
                    profileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    name = "seehyang",
                    tags = "#머스크",
                    likeCount = 127
                ), PerfumeDetailData.PerfumeDetailItem(
                    photo = "https://images.unsplash.com/photo-1633074320366-365b5e382fb5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1374&q=80",
                    profileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    name = "seehyang",
                    tags = "#시트러스",
                    likeCount = 325
                ), PerfumeDetailData.PerfumeDetailItem(
                    photo = "https://images.unsplash.com/photo-1633124890681-fc9afca4c4ac?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                    profileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    name = "seehyang",
                    tags = "#우디 #플로럴",
                    likeCount = 601
                ), PerfumeDetailData.PerfumeDetailItem(
                    photo = "https://images.unsplash.com/photo-1633131902932-31ba72ee19d7?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1470&q=80",
                    profileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    name = "seehyang",
                    tags = "#플로럴 #머스크 #우디",
                    likeCount = 125
                ), PerfumeDetailData.PerfumeDetailItem(
                    photo = "https://images.unsplash.com/photo-1633166158652-22faaa722751?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=687&q=80",
                    profileImage = "https://images.unsplash.com/photo-1633098205447-de387b769109?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=765&q=80",
                    name = "seehyang",
                    tags = "#플로럴",
                    likeCount = 100
                )
            )
        )
    }
}