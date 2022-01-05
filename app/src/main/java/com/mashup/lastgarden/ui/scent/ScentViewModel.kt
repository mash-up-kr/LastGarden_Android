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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScentViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private var _storyPosition: MutableLiveData<Int> = savedStateHandle.getLiveData("storyIndex")
    val storyPosition: LiveData<Int>
        get() = _storyPosition

    private var _sortOrder = MutableLiveData(Sort.LATEST)
    val sortOrder: LiveData<Sort>
        get() = _sortOrder

    private val _perfumeStory = MutableLiveData<Story>()
    val perfumeStory: LiveData<Story>
        get() = _perfumeStory

    private val _perfumeStoryList = MutableLiveData<List<StoryItem>>()
    val perfumeStoryList: LiveData<List<StoryItem>>
        get() = _perfumeStoryList

    private val _emitStoryList = MutableLiveData<Unit>()
    val emitStoryList: LiveData<Unit>
        get() = _emitStoryList

    private val _likedStoryList = MutableStateFlow(emptyList<Int>())
    val likedStoryList: StateFlow<List<Int>> = _likedStoryList

    private val _storySize = MutableLiveData(0)
    val storySize: LiveData<Int> = _storySize

    lateinit var pagingStoryList: Flow<PagingData<Story>>

    private val _storyId: LiveData<Int?> = savedStateHandle.getLiveData("storyId", null)
    private val _perfumeId: LiveData<Int?> = savedStateHandle.getLiveData("perfumeId", null)
    private val storyIdAndPerfumeIdSet: LiveData<MainStorySet?> =
        savedStateHandle.getLiveData("mainStorySet", null)

    init {
        getStorySize()
        getAllStories()
    }

    fun setSortOrder(sort: Sort) {
        _sortOrder.value = sort
    }

    fun setScrollPosition(position: Int) {
        if (_storyPosition.value != position) {
            _storyPosition.value = position
        }
    }

    private fun getTodayAndHotStoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            val todayAndHotStoryList = mutableListOf<StoryItem>()
            storyIdAndPerfumeIdSet.value?.set?.forEach { pair ->
                storyRepository.fetchPerfumeStory(storyId = pair.first)?.let { story ->
                    todayAndHotStoryList.add(story.toStoryItem())
                }
            }
            _perfumeStoryList.postValue(todayAndHotStoryList)
        }
    }

    private fun getPerfumeStoryList() {
        val perfumeId = _perfumeId.value ?: return
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

    private fun getPerfumeStory() {
        val storyId = _storyId.value ?: return
        viewModelScope.launch {
            _perfumeStory.value = storyRepository.fetchPerfumeStory(storyId)
        }
    }

    private fun getAllStories() {
        getTodayAndHotStoryList()
        getPerfumeStoryList()
        getPerfumeStory()
    }

    private fun getStorySize() {
        val perfumeId = _perfumeId.value ?: return
        viewModelScope.launch {
            _storySize.value = storyRepository.getStoryCount(perfumeId)
        }
    }

    fun likeStory(storyId: Int) {
        viewModelScope.launch {
            storyRepository.likeStory(storyId)
            _likedStoryList.emit(
                likedStoryList.value.toMutableList().apply {
                    if (contains(storyId)) {
                        remove(storyId)
                    } else {
                        add(storyId)
                    }
                }
            )
            getAllStories()
        }
    }
}