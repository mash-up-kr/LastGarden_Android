package com.mashup.lastgarden.ui.scent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.lastgarden.data.repository.StoryRepository
import com.mashup.lastgarden.data.vo.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScentViewModel @Inject constructor(
    private val storyRepository: StoryRepository
) : ViewModel() {

    private var _sortOrder = MutableLiveData(Sort.POPULARITY)
    val sortOrder: LiveData<Sort>
        get() = _sortOrder

    private val _perfumeStoryList = MutableLiveData<List<Story?>?>()
    val perfumeStoryList: LiveData<List<Story?>?>
        get() = _perfumeStoryList

    private val list = mutableListOf<Story?>()

    fun setSortOrder(sort: Sort) {
        _sortOrder.value = sort
    }

    fun getPerfumeStoryList(storyId: Int) {
        viewModelScope.launch {
            _perfumeStoryList.value = storyRepository.fetchPerfumeStoryList(storyId)
        }
    }

    fun getPerfumeStory(storyId: Int) {
        viewModelScope.launch {
            list.add(storyRepository.fetchPerfumeStory(storyId))
            _perfumeStoryList.value = list
        }
    }

    fun getPerfumeStoryLike(storyId: Int) {
        viewModelScope.launch {
            //TODO token 나중에 제거
            val token = ""
            storyRepository.getStoryLike(token, storyId)
            //TODO 좋아요 API 호출 후 리스트 다시 가져오기
        }
    }
}