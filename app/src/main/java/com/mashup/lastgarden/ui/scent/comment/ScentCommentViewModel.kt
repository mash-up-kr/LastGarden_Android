package com.mashup.lastgarden.ui.scent.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mashup.lastgarden.data.repository.StoryRepository
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.data.vo.Reply
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScentCommentViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    enum class AddCommentState {
        SUCCESS, FAILURE
    }

    lateinit var pagingCommentList: Flow<PagingData<Comment>>

    private val _replyList = MutableLiveData<List<Reply>>()
    val replyList: LiveData<List<Reply>>
        get() = _replyList

    private val _commentDetail = MutableLiveData<Comment>()
    val commentDetail: LiveData<Comment>
        get() = _commentDetail

    private val _commentResponse = MutableLiveData<AddCommentState>()
    val commentResponse: LiveData<AddCommentState>
        get() = _commentResponse

    private val _emitCommentList = MutableLiveData<Unit>()
    val emitCommentList: LiveData<Unit>
        get() = _emitCommentList

    val storyId: LiveData<Int> = savedStateHandle.getLiveData("storyId", 0)

    fun setStoryId(storyId: Int) {
        savedStateHandle["storyId"] = storyId
    }

    fun getCommentList(storyId: Int) {
        _emitCommentList.value = Unit
        pagingCommentList =
            storyRepository
                .fetchCommentList(storyId, PAGE_SIZE)
                .cachedIn(viewModelScope)
    }

    fun addComment(storyId: Int, contents: String) {
        viewModelScope.launch {
            if (contents.isNotEmpty()) {
                val response = storyRepository.addComment(storyId, contents)
                if (response == "OK") {
                    _commentResponse.value = AddCommentState.SUCCESS
                } else {
                    _commentResponse.value = AddCommentState.FAILURE
                }
            }
        }
    }

    fun getReplyList() {
        _replyList.value =
            listOf(
                Reply(0, "일이삼", "2020-12-12", "sdfsdfsdffd", 112, 11, true),
                Reply(0, "일이삼", "2020-12-12", "sdfsdfsdffd", 112, 11, true),
                Reply(0, "일이삼", "2020-12-12", "sdfsdfsdffd", 112, 11, false),
                Reply(0, "일이삼", "2020-12-12", "sdfsdfsdffd", 112, 11, false)
            )
    }

    fun setCommentData(comment: Comment) {
        _commentDetail.value = comment
    }
}