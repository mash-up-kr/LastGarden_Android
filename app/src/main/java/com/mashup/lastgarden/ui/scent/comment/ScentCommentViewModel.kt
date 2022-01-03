package com.mashup.lastgarden.ui.scent.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mashup.lastgarden.data.repository.StoryRepository
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.data.vo.Reply
import com.mashup.lastgarden.ui.scent.ScentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ScentCommentViewModel @Inject constructor(
    private val storyRepository: StoryRepository
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    lateinit var pagingCommentList: Flow<PagingData<Comment>>

    private val _replyList = MutableLiveData<List<Reply>>()
    val replyList: LiveData<List<Reply>>
        get() = _replyList

    private val _commentDetail = MutableLiveData<Comment>()
    val commentDetail: LiveData<Comment>
        get() = _commentDetail

    fun getCommentList(storyId: Int) {
        pagingCommentList =
            storyRepository
                .fetchCommentList(storyId, PAGE_SIZE)
                .cachedIn(viewModelScope)
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