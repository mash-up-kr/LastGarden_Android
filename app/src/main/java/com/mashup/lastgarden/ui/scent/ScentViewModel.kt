package com.mashup.lastgarden.ui.scent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val storyRepository: StoryRepository
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


    private val _likedStoryList = MutableStateFlow(emptyList<Int>())
    val likedStoryList: StateFlow<List<Int>> = _likedStoryList

    private val _storySize = MutableLiveData(0)
    val storySize: LiveData<Int> = _storySize

    private val todayAndHotStoryList = mutableListOf<Story>()

    fun setSortOrder(sort: Sort) {
        _sortOrder.value = sort
    }

    fun setScrollPosition(storyPosition: Int) {
        _storyPosition.value = storyPosition
    }

    fun getTodayAndHotStoryList(storyIdAndPerfumeIdSet: MainStorySet) {
        viewModelScope.launch {
            todayAndHotStoryList.clear()
            storyIdAndPerfumeIdSet.set?.forEach {
                storyRepository.fetchPerfumeStory(storyId = it.first)?.let { story ->
                    todayAndHotStoryList.add(story)
                }
            }
            _perfumeStoryList.value = todayAndHotStoryList
        }
    }

    fun getPerfumeStoryLists(perfumeId: Int): Flow<PagingData<Story>> =
        storyRepository.fetchPerfumeStoryList(perfumeId, PAGE_SIZE)
            .cachedIn(viewModelScope)
            .combine(likedStoryList) { pagingData, likedItem ->
                pagingData.map { story ->
                    story.copy(
                        isLiked = likedItem.contains(story.storyId)
                    )
                }
            }

    fun getStorySize(perfumeId: Int) {
        viewModelScope.launch {
            _storySize.value = storyRepository.getStoryCount(perfumeId)
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