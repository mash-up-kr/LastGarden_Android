package com.mashup.lastgarden.ui.scent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.lastgarden.data.repository.StoryRepository
import com.mashup.lastgarden.data.vo.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScentViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private var _storyPosition = MutableLiveData<Int>()
    val storyPosition: LiveData<Int>
        get() = _storyPosition

    private var _sortOrder = MutableLiveData(Sort.LATEST)
    val sortOrder: LiveData<Sort>
        get() = _sortOrder

    private val _perfumeStory = MutableLiveData<Story>()
    val perfumeStory: LiveData<Story>
        get() = _perfumeStory

    private val _perfumeStoryList = MutableLiveData<List<Story>>()
    val perfumeStoryList: LiveData<List<Story>>
        get() = _perfumeStoryList

    private val _emitStoryList = MutableLiveData<Unit>()
    val emitStoryList: LiveData<Unit>
        get() = _emitStoryList

    private val _likedStoryList = MutableStateFlow(emptyList<Int>())
    val likedStoryList: StateFlow<List<Int>> = _likedStoryList

    private val todayAndHotStoryList = mutableListOf<Story>()
    lateinit var pagingStoryList: Flow<PagingData<Story>>

    val storyId: LiveData<Int> = savedStateHandle.getLiveData("storyId", 0)
    val perfumeId: LiveData<Int> = savedStateHandle.getLiveData("perfumeId", 0)
    val storyIdAndPerfumeIdSet: LiveData<MainStorySet> =
        savedStateHandle.getLiveData("storyIdAndPerfumeIdSet", null)

    fun setSortOrder(sort: Sort) {
        _sortOrder.value = sort
    }

    fun setStoryId(storyId: Int) {
        savedStateHandle["storyId"] = storyId
    }

    fun setPerfumeId(perfumeId: Int) {
        savedStateHandle["perfumeId"] = perfumeId
    }

    fun setMainStoryList(storyIdAndPerfumeIdSet: MainStorySet?) {
        savedStateHandle["storyIdAndPerfumeIdSet"] = storyIdAndPerfumeIdSet
    }

    fun setScrollPosition(position: Int) {
        _storyPosition.value = position
    }

    fun getTodayAndHotStoryList(storyIdAndPerfumeIdSet: MainStorySet) {
        viewModelScope.launch {
            todayAndHotStoryList.clear()
            storyIdAndPerfumeIdSet.set?.forEach { pair ->
                storyRepository.fetchPerfumeStory(storyId = pair.first)?.let { story ->
                    todayAndHotStoryList.add(story)
                }
            }
            _perfumeStoryList.value = todayAndHotStoryList
        }
    }

    fun getPerfumeStoryList(perfumeId: Int) {
        _emitStoryList.value = Unit
        pagingStoryList = storyRepository.fetchPerfumeStoryList(perfumeId, PAGE_SIZE)
            .cachedIn(viewModelScope)
            .combine(likedStoryList) { pagingData, likedItem ->
                pagingData.map { story ->
                    story.copy(
                        isLiked = likedItem.contains(story.storyId)
                    )
                }
            }
    }


    fun getPerfumeStory(storyId: Int) {
        viewModelScope.launch {
            _perfumeStory.value = storyRepository.fetchPerfumeStory(storyId)
        }
    }

    fun getPerfumeStoryLike(storyId: Int) {
        viewModelScope.launch {
            storyRepository.getStoryLike(storyId)
            _likedStoryList.emit(
                likedStoryList.value.toMutableList().apply {
                    if (contains(storyId)) {
                        remove(storyId)
                    } else {
                        add(storyId)
                    }
                }
            )
        }
    }
}