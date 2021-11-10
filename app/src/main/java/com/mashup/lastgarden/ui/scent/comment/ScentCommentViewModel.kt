package com.mashup.lastgarden.ui.scent.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.data.vo.Reply

class ScentCommentViewModel : ViewModel() {

    private val _commentList = MutableLiveData<List<Comment>>()
    val commentList: LiveData<List<Comment>>
        get() = _commentList

    private val _replyList = MutableLiveData<List<Reply>>()
    val replyList: LiveData<List<Reply>>
        get() = _replyList

    private val _commentDetail = MutableLiveData<Comment>()
    val commentDetail: LiveData<Comment>
        get() = _commentDetail

    fun getCommentList() {
        val replyList = listOf<Reply>()
        _commentList.value =
            listOf(
                Comment(0, "일이삼", "2020-12-12", "sdfsdfsdffd", replyList, 112, 11, 10, true),
                Comment(0, "일이삼", "2020-12-12", "sdfsdfsdffd", replyList, 112, 11, 10, true),
                Comment(0, "일이삼", "2020-12-12", "sdfsdfsdffd", replyList, 112, 11, 10, false),
                Comment(0, "일이삼", "2020-12-12", "sdfsdfsdffd", replyList, 112, 11, 10, false),
                Comment(0, "일이삼", "2020-12-12", "sdfsdfsdffd", replyList, 112, 11, 10, true),
                Comment(0, "일이삼", "2020-12-12", "sdfsdfsdffd", replyList, 112, 11, 10, false),
                Comment(0, "일이삼", "2020-12-12", "sdfsdfsdffd", replyList, 112, 11, 10, true)
            )
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