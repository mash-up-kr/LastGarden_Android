package com.mashup.lastgarden.data.remote

import com.mashup.base.extensions.requestBodyOf
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.LikeResponse
import com.mashup.lastgarden.network.response.onErrorReturnData
import com.mashup.lastgarden.network.response.onErrorReturnDataNull
import com.mashup.lastgarden.network.services.StoryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRemoteDataSource @Inject constructor(private val service: StoryService) {

    suspend fun fetchHotStory(): List<Story> =
        service.getHotStories().onErrorReturnData(emptyList())

    suspend fun getPerfumeStoryList(storyId: Int, cursor: Int? = null): List<Story> =
        service.getPerfumeStoryList(storyId, cursor).onErrorReturnData(emptyList())

    suspend fun getPerfumeStory(storyId: Int): Story? =
        service.getPerfumeStory(storyId).onErrorReturnDataNull()

    suspend fun getStoryLike(storyId: Int): LikeResponse? =
        service.getStoryLike(storyId).onErrorReturnDataNull()

    suspend fun uploadStory(perfumeId: Int?, imageId: Int, tags: List<String>) =
        service.uploadStory(
            requestBodyOf {
                "perfumeId" to perfumeId
                "imageId" to imageId
                "tags" to tags
            }
        ).onErrorReturnDataNull()

    suspend fun getStoryCount(perfumeId: Int): Int? =
        service.getStoryCount(perfumeId).onErrorReturnDataNull()
}