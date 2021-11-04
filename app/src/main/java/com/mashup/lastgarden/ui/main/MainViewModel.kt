package com.mashup.lastgarden.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.base.extensions.combine
import com.mashup.lastgarden.data.PerfumeSharedPreferences
import com.mashup.lastgarden.data.repository.PerfumeRepository
import com.mashup.lastgarden.data.repository.StoryRepository
import com.mashup.lastgarden.data.vo.PerfumeAndStories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val storyRepository: StoryRepository,
    private val sharedPreferences: PerfumeSharedPreferences
) : ViewModel() {

    private val _isShowBanner = MutableStateFlow(true)

    init {
        loadIsShowBanner()
    }

    private val _todayPerfumeAndStories = MutableStateFlow<PerfumeAndStories?>(null)

    init {
        refreshTodayPerfume()
    }

    private val todayPerfumeItem: StateFlow<MainAdapterItem?> = _todayPerfumeAndStories
        .map { it?.perfume?.toMainAdapterItem() }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val todayPerfumeStories: StateFlow<List<MainAdapterItem.TodayPerfumeStories.TodayPerfumeStoryItem>> =
        _todayPerfumeAndStories
            .map { it?.stories?.toTodayPerfumeStoryItems() ?: emptyList() }
            .distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun refreshTodayPerfume() {
        viewModelScope.launch(Dispatchers.IO) {
            _todayPerfumeAndStories.value = perfumeRepository.fetchTodayPerfume()
        }
    }

    private val _hotStoriesItem =
        MutableStateFlow<List<MainAdapterItem.HotStories.HotStoryItem>>(emptyList())
    val hotStories: StateFlow<List<MainAdapterItem.HotStories.HotStoryItem>> =
        _hotStoriesItem.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _hotStoriesItem.value = storyRepository.fetchHotStory().toHotStoryItems()
        }
    }

    private val _rankingsItem =
        MutableStateFlow<List<MainAdapterItem.PerfumeRankings.PerfumeRankingItem>>(emptyList())
    val rankingsItem: StateFlow<List<MainAdapterItem.PerfumeRankings.PerfumeRankingItem>> =
        _rankingsItem.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _rankingsItem.value = perfumeRepository.fetchWeeklyRanking().toPerfumeRankingItems()
        }
    }

    private val _recommendsItem =
        MutableStateFlow<List<MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem>>(emptyList())
    val recommendsItem: StateFlow<List<MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem>> =
        _recommendsItem.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _recommendsItem.value =
                perfumeRepository.fetchSteadyPerfumes().toRecommendPerfumeItems()
        }
    }

    private val _mainItems = MutableStateFlow<List<MainAdapterItem>>(emptyList())
    val mainItems: StateFlow<List<MainAdapterItem>> = _mainItems

    init {
        viewModelScope.launch {
            todayPerfumeItem.combine(
                todayPerfumeStories.combine(_isShowBanner),
                _hotStoriesItem.combine(rankingsItem, recommendsItem),
            )
                .map { (todayPerfume, todayPerfumeStoriesAndIsShowBanner, hotStoriesAndRankingsAndRecommendsItem) ->
                    val (todayPerfumeStories, isShowBanner) = todayPerfumeStoriesAndIsShowBanner
                    val (hotStoriesItem, rankingsItem, recommendsItem) = hotStoriesAndRankingsAndRecommendsItem
                    listOfNotNull(
                        todayPerfume,
                        todayPerfumeStories.toMainAdapterItem(),
                        MainAdapterItem.RefreshAnotherPerfume
                    ) + if (rankingsItem.isNotEmpty()) {
                        listOfNotNull(
                            MainAdapterItem.PerfumeRankingsHeader,
                            MainAdapterItem.PerfumeRankings(rankingsItem)
                        )
                    } else {
                        emptyList()
                    } + if (hotStoriesItem.isNotEmpty()) {
                        listOfNotNull(
                            MainAdapterItem.HotStoryHeader,
                            MainAdapterItem.HotStories(hotStoriesItem)
                        )
                    } else {
                        emptyList()
                    } + if (isShowBanner) {
                        listOfNotNull(MainAdapterItem.Banner)
                    } else {
                        emptyList()
                    } + if (recommendsItem.isNotEmpty()) {
                        listOfNotNull(
                            MainAdapterItem.PerfumeRecommendsHeader,
                            MainAdapterItem.PerfumeRecommends(recommendsItem),
                        )
                    } else {
                        emptyList()
                    } + listOfNotNull(MainAdapterItem.SeeMore)
                }
                .collectLatest { _mainItems.value = it }
        }
    }


    fun setIsShowBanner(isShow: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPreferences.saveIsShowBanner(isShow)
        }
        loadIsShowBanner()
    }

    private fun loadIsShowBanner() {
        viewModelScope.launch(Dispatchers.IO) {
            _isShowBanner.value = sharedPreferences.getIsShowBanner()
        }
    }
}