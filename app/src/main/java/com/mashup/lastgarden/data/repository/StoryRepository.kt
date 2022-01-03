package com.mashup.lastgarden.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mashup.lastgarden.data.paging.PerfumeCommentPagingSource
import com.mashup.lastgarden.data.paging.PerfumeStoryPagingSource
import com.mashup.lastgarden.data.remote.StoryRemoteDataSource
import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.LikeResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepository @Inject constructor(
    private val remote: StoryRemoteDataSource,
) {
    suspend fun fetchHotStory(): List<Story> = remote.fetchHotStory()

    fun fetchPerfumeStoryList(id: Int, pageSize: Int): Flow<PagingData<Story>> =
        Pager(PagingConfig(pageSize)) {
            PerfumeStoryPagingSource(id, remote)
        }.flow

    suspend fun fetchPerfumeStory(storyId: Int): Story? =
        remote.getPerfumeStory(storyId)

    suspend fun getStoryLike(storyId: Int): LikeResponse? =
        remote.getStoryLike(storyId)

    suspend fun uploadStory(perfumeId: Int?, imageId: Int, tags: List<String>) =
        remote.uploadStory(perfumeId, imageId, tags)

    fun fetchCommentList(id: Int, pageSize: Int): Flow<PagingData<Comment>> =
        Pager(PagingConfig(pageSize)) {
            PerfumeCommentPagingSource(id, remote)
        }.flow
}