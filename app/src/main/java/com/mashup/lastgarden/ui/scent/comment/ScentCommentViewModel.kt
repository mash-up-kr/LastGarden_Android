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
import com.mashup.lastgarden.network.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScentCommentViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    savedStateHandle: SavedStateHandle
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

    private val _addCommentSuccess = MutableLiveData<AddCommentState>()
    val addCommentSuccess: LiveData<AddCommentState>
        get() = _addCommentSuccess

    private val _emittedCommentList = MutableLiveData<Unit>()
    val emittedCommentList: LiveData<Unit>
        get() = _emittedCommentList

    private val _storyId: LiveData<Int> = savedStateHandle.getLiveData("storyId", null)

    init {
        getCommentList()
    }

    private fun getCommentList() {
        val storyId = _storyId.value ?: return
        _emittedCommentList.value = Unit
        pagingCommentList = storyRepository
            .fetchCommentList(storyId, PAGE_SIZE)
            .cachedIn(viewModelScope)
    }

    fun addComment(contents: String) {
        val storyId = _storyId.value ?: return
        viewModelScope.launch {
            if (contents.isNotEmpty()) {
                val result = storyRepository.addComment(storyId, contents)
                when (result.data) {
                    ResponseData.SUCCESS ->
                        _addCommentSuccess.value = AddCommentState.SUCCESS
                    else ->
                        _addCommentSuccess.value = AddCommentState.FAILURE
                }
            }
            getCommentList()
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