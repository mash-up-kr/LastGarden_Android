package com.mashup.lastgarden.ui.scent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mashup.lastgarden.data.repository.StoryRepository
import com.mashup.lastgarden.data.vo.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScentViewModel @Inject constructor(
    private val storyRepository: StoryRepository
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private var _storyIndex = MutableLiveData<Int>()
    val storyIndex: LiveData<Int>
        get() = _storyIndex

    private var _sortOrder = MutableLiveData(Sort.LATEST)
    val sortOrder: LiveData<Sort>
        get() = _sortOrder

    private val _perfumeStory = MutableLiveData<Story?>()
    val perfumeStory: LiveData<Story?>
        get() = _perfumeStory

    private val _perfumeStoryList = MutableLiveData<List<Story>>()
    val perfumeStoryList: LiveData<List<Story>>
        get() = _perfumeStoryList

    private val todayAndHotStoryList = mutableListOf<Story>()

    fun setSortOrder(sort: Sort) {
        _sortOrder.value = sort
    }

    fun getTodayAndHotStoryList(storyIdAndPerfumeIdSet: MainStorySet, storyIndex: Int) {
        viewModelScope.launch {
            storyIdAndPerfumeIdSet.set?.forEach { pair ->
                storyRepository.fetchPerfumeStory(storyId = pair.first)?.let { story ->
                    todayAndHotStoryList.add(story)
                }
            }
            _perfumeStoryList.value = todayAndHotStoryList
            _storyIndex.value = storyIndex
        }
    }

    fun getPerfumeStoryLists(perfumeId: Int): Flow<PagingData<Story>> =
        storyRepository
            .fetchPerfumeStoryList(perfumeId, PAGE_SIZE)
            .cachedIn(viewModelScope)

    fun getPerfumeStory(storyId: Int) {
        viewModelScope.launch {
            _perfumeStory.value = storyRepository.fetchPerfumeStory(storyId)
        }
    }

    fun getPerfumeStoryLike(storyId: Int) {
        viewModelScope.launch {
            storyRepository.getStoryLike(storyId)
            //TODO 좋아요 API 호출 후 리스트 다시 가져오기
        }
    }
}